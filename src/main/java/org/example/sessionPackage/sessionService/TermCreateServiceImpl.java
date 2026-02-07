//package org.example.sessionPackage.sessionService;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.example.data.model.*;
//import org.example.data.repositories.SchoolBranchRepository;
//import org.example.data.repositories.SessionRepository;
//import org.example.sessionPackage.dto.sessionRequest.SessionRequest;
//import org.example.sessionPackage.dto.sessionResponse.SessionResponse;
//import org.example.utilities.Utilities;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class SessionCreateServiceImpl implements SessionCreateService {
//
//    private final SessionRepository sessionRepository;
//    private final SchoolBranchRepository branchRepository;
//    private final TenantContextResolver tenantResolver;
//    private final SessionValidator sessionValidator;
//
//    @Override
//    public SessionResponse create(SessionRequest request) {
//
//        sessionValidator.validateDates(request.getStartDate(), request.getEndDate());
//        AdminTenant tenant = tenantResolver.currentTenant();
//        SchoolBranch branch = resolveBranch(request.getBranchId(), tenant);
////        deactivateExistingSessions(tenant, branch);
//
//        boolean exists = sessionRepository
//                .existsByAdminTenantAndStartDateAndEndDateAndSchoolBranch(
//                        tenant,
//                        request.getStartDate(),
//                        request.getEndDate(),
//                        branch
//                );
//        if (exists) {throw new IllegalArgumentException(Utilities.DUPLICATE_SESSION_FOR_THIS_SESSION);}
//        Session session = Session.builder()
//                .adminTenant(tenant)
//                .schoolBranch(branch)
//                .sessionYear(request.getSessionYear())
//                .term(request.getTerm())
//                .startDate(request.getStartDate())
//                .endDate(request.getEndDate())
//                .status(SessionStatus.PLANNED)
//                .isActive(false)
//                .build();
//        sessionRepository.save(session);
//
//        return SessionResponse.builder()
//                .message(Utilities.SESSION_CREATED_SUCCESSFULLY)
//                .createdAt(new Date())
//                .build();
//    }
//
//    private SchoolBranch resolveBranch(UUID branchId, AdminTenant tenant) {
//        if (branchId == null) return null;
//
//        SchoolBranch branch = branchRepository.findById(branchId).orElseThrow(() -> new IllegalArgumentException(Utilities.BRANCH_NOT_FOUND));
//        sessionValidator.validateBranchOwnership(branch, tenant);
//        return branch;
//    }
//
////    private void deactivateExistingSessions(AdminTenant tenant, SchoolBranch branch) {
////        if (branch != null) {
////            sessionRepository.deactivateAllForTenantAndBranch(
////                    tenant.getTenantId(), branch
////            );
////        } else {
////            sessionRepository.deactivateAllForTenant(tenant.getTenantId());
////        }
////    }
//}
package org.example.sessionPackage.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.data.model.*;
import org.example.data.repositories.AcademicSessionRepository;
import org.example.data.repositories.AdminTenantRepository;
import org.example.data.repositories.TermRepository;
import org.example.sessionPackage.dto.sessionRequest.TermRequest;
import org.example.sessionPackage.dto.sessionResponse.TermResponse;
import org.example.utilities.Utilities;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TermCreateServiceImpl implements TermCreateService {

    private final TermRepository termRepository;
    private final AdminTenantRepository adminTenantRepository;
    private final TenantContextResolver tenantResolver;
    private final AcademicSessionRepository academicSessionRepository;
    private final TermValidator termValidator;

    private static final int MAX_PAST_DAYS_FOR_START_DATE = 21; // 3 weeks

    @Override
    public TermResponse create(TermRequest request) {

        UUID tenantId = tenantResolver.currentTenantId();
        AdminTenant tenant = adminTenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalStateException("Tenant not found"));

        // Resolve branch if provided (optional)

        termValidator.validateDates(
                request.getStartDate(),
                request.getEndDate(),
                tenant,
                null,
                null   // current session is null when creating
        );

        // 4️⃣ Enforce date sanity (business rule)
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate startDateLocal = request.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate earliestAllowed = today.minusDays(MAX_PAST_DAYS_FOR_START_DATE);
        if (startDateLocal.isBefore(earliestAllowed)) {
            throw new IllegalArgumentException(
                    String.format(
                            "Start date cannot be more than %d days in the past. Earliest allowed: %s",
                            MAX_PAST_DAYS_FOR_START_DATE,
                            earliestAllowed
                    )
            );
        }

        // 5️⃣ Prevent duplicates (tenant-scoped)
//        boolean exists = sessionRepository.existsByAdminTenantAndStartDateAndEndDate(
        boolean exists = termRepository.existsByAdminTenant_TenantIdAndStartDateAndEndDate(
                tenant.getTenantId(),
                request.getStartDate(),
                request.getEndDate()
        );

        if (exists) {
            throw new IllegalArgumentException(Utilities.DUPLICATE_SESSION_FOR_THIS_SESSION);
        }

        AcademicSessionEntity academicSession =
                academicSessionRepository
                        .findByTenant_TenantIdAndSessionYear(
                                tenant.getTenantId(),
                                request.getAcademicSession()
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Academic session does not exist"
                                )
                        );

        // Max 3 terms per session
        if (termRepository.countByAcademicSession(academicSession) >= 3) {
            throw new IllegalStateException(
                    "An academic session cannot have more than 3 terms"
            );
        }

// No duplicate term type
        boolean termExists =
                termRepository.existsByAcademicSessionAndTerm(
                        academicSession,
                        request.getTerm()
                );

        if (termExists) {
            throw new IllegalStateException(
                    "This term already exists in the academic session"
            );
        }



        // 6️⃣ Create session
        Term term = Term.builder()
                .adminTenant(tenant)
                .academicSession(academicSession)
                .term(request.getTerm())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(TermStatus.PLANNED)
//                .isActive(false)
                .build();

        Term savedTerm = termRepository.save(term);

        return TermResponse.builder()
                .schoolName(tenant.getSchoolName())
                .academicSession(savedTerm.getAcademicSession().getSessionYear())
                .term(savedTerm.getTerm())
                .startDate(
                        term.getStartDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                )
                .endDate(
                        term.getEndDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                )
                .status(savedTerm.getStatus())
                .createdAt(term.getCreatedAt())
                .sessionId(savedTerm.getTermId())
                .tenantId(tenant.getTenantId())
                .message(Utilities.SESSION_CREATED_SUCCESSFULLY)
                .build();
    }
}
