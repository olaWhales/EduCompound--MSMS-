//package org.example.services.sessionService;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.example.data.model.*;
//import org.example.data.repositories.SchoolBranchRepository;
//import org.example.data.repositories.SessionRepository;
//import org.example.dto.requests.sessionRequest.SessionRequest;
//import org.example.dto.responses.sessionResponse.SessionResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//@RequiredArgsConstructor
//public class SessionServiceImp implements SessionService {
//
//    private final SessionRepository sessionRepository;
//    private final SchoolBranchRepository branchRepository;
//
//    @Transactional
//    public SessionResponse sessionResponse(SessionRequest sessionRequest) {
//        // Validate start and end dates
//        if (sessionRequest.getStartDate() == null || sessionRequest.getEndDate() == null) {
//            throw new IllegalArgumentException("Start date and end date are required");
//        }
//        if (sessionRequest.getStartDate().after(sessionRequest.getEndDate())) {
//            throw new IllegalArgumentException("Start date must be before end date");
//        }
//
//
//
//        // Get current user and tenant
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
//        Users currentUser = principal.users();
//        AdminTenant tenant = currentUser.getAdminTenant();
//
//        // Handle optional branch
//        SchoolBranch branch = null;
//        if (sessionRequest.getBranchId() != null) {
//            branch = branchRepository.findById(sessionRequest.getBranchId())
//                    .orElseThrow(() -> new IllegalArgumentException("Branch not found"));
//
//            if (!branch.getAdminTenant().equals(tenant)) {
//                throw new IllegalArgumentException("Branch does not belong to your school.");
//            }
//        }
//        if (sessionRepository.existsByTenantIdAndIsActiveTrue(tenantId)) {
//            sessionRepository.deactivateAllForTenant(tenantId); // write a custom JPQL update
//        }
//
//
//        // Check for duplicate session for this tenant and (optional) branch
//        boolean exists = sessionRepository.existsByAdminTenantAndStartDateAndEndDateAndSchoolBranch(
//                tenant,
//                sessionRequest.getStartDate(),
//                sessionRequest.getEndDate(),
//                branch
//        );
//
//        if (exists) {
//            throw new IllegalArgumentException("A session with the same dates already exists for this tenant/branch.");
//        }
//
//        // Create and save session
//        Session session = Session.builder()
//                .adminTenant(tenant)
//                .schoolBranch(branch) // can be null
//                .isActive(true)
//                .sessionYear(sessionRequest.getSessionYear())
//                .term(sessionRequest.getTerm())
//                .startDate(sessionRequest.getStartDate())
//                .endDate(sessionRequest.getEndDate())
//                .build();
//        sessionRepository.save(session);
//
//        return SessionResponse.builder()
//                .sessionId(session.getSessionId())
//                .message("Session created successfully")
//                .createdAt(new Date())
//                .build();
//    }
//}


package org.example.sessionPackage.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.*;
import org.example.data.repositories.SchoolBranchRepository;
import org.example.data.repositories.SessionRepository;
import org.example.sessionPackage.dto.sessionRequest.SessionRequest;
import org.example.sessionPackage.dto.sessionResponse.SessionResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionCreateServiceImpl implements SessionCreateService {

    private final SessionRepository sessionRepository;
    private final SchoolBranchRepository branchRepository;
    private final TenantContextResolver tenantResolver;
    private final SessionValidator sessionValidator;

    @Override
    public SessionResponse create(SessionRequest request) {

        sessionValidator.validateDates(
                request.getStartDate(),
                request.getEndDate()
        );

        AdminTenant tenant = tenantResolver.currentTenant();

        SchoolBranch branch = resolveBranch(request.getBranchId(), tenant);

        deactivateExistingSessions(tenant, branch);

        boolean exists = sessionRepository
                .existsByAdminTenantAndStartDateAndEndDateAndSchoolBranch(
                        tenant,
                        request.getStartDate(),
                        request.getEndDate(),
                        branch
                );

        if (exists) {
            throw new IllegalArgumentException("Duplicate session for this tenant/branch");
        }

        Session session = Session.builder()
                .adminTenant(tenant)
                .schoolBranch(branch)
                .sessionYear(request.getSessionYear())
                .term(request.getTerm())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isActive(true)
                .build();

        sessionRepository.save(session);

        return SessionResponse.builder()
                .message("Session created successfully")
                .createdAt(new Date())
                .build();
    }

    private SchoolBranch resolveBranch(UUID branchId, AdminTenant tenant) {
        if (branchId == null) return null;

        SchoolBranch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found"));

        sessionValidator.validateBranchOwnership(branch, tenant);
        return branch;
    }

    private void deactivateExistingSessions(AdminTenant tenant, SchoolBranch branch) {
        if (branch != null) {
            sessionRepository.deactivateAllForTenantAndBranch(
                    tenant.getTenantId(), branch
            );
        } else {
            sessionRepository.deactivateAllForTenant(tenant.getTenantId());
        }
    }
}
