package org.example.sessionPackage.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.*;
import org.example.data.repositories.AcademicSessionRepository;
import org.example.data.repositories.TermRepository;
import org.example.sessionPackage.dto.sessionRequest.TermRequest;
import org.example.sessionPackage.dto.sessionResponse.TermResponse;
import org.example.utilities.Utilities;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TermUpdateServiceImpl implements TermUpdateService {

    private final TermRepository termRepository;
    private final TenantContextResolver tenantResolver;
    private final TermValidator termValidator;
    private final AcademicSessionRepository academicSessionRepository;
//    private final AuthenticatedUserProvider authUserProvider;

    @Override
    public TermResponse update(UUID sessionId, TermRequest request) {
        UUID tenant = tenantResolver.currentTenantId();

        Term term = termRepository.findById(sessionId).orElseThrow(() -> new IllegalStateException(Utilities.SESSION_NOT_FOUND));

        // Fetch the AcademicSessionEntity from DB based on tenant + session year
        AcademicSessionEntity academicSession = academicSessionRepository
                .findByTenant_TenantIdAndSessionYear(tenant, request.getAcademicSession())
                .orElseThrow(() -> new IllegalArgumentException("Academic session not found"));

// Assign the entity to the term
        term.setAcademicSession(academicSession);


        /* 🔐 Tenant isolation */
        if (!term.getAdminTenant().getTenantId().equals(tenant)) {
            throw new AccessDeniedException(
                    Utilities.CANNOT_UPDATE_SESSION_FROM_ANOTHER_TENANT
            );
        }

        /* 🔒 Lifecycle lock */
        if (term.getStatus() != TermStatus.PLANNED) {
            throw new IllegalStateException(
                    "Session can only be updated while in PLANNED state"
            );
        }

        /* 📅 Validation */
        termValidator.validateDates(
                request.getStartDate(),
                request.getEndDate(),
                term.getAdminTenant(),   // tenant
                term.getSchoolBranch(),  // branch (nullable)
                term                     // current session, to ignore itself in overlaps
        );

        /* ✏️ Allowed updates */
        term.setAcademicSession(academicSession);
        term.setTerm(request.getTerm());
        term.setStartDate(request.getStartDate());
        term.setEndDate(request.getEndDate());

        Term savedTerm = termRepository.save(term);

        // Build and return SessionResponse
        return TermResponse.builder()
                .schoolName(savedTerm.getAdminTenant().getSchoolName())
                .academicSession(savedTerm.getAcademicSession().getSessionYear())
                .term(savedTerm.getTerm())
                .startDate(savedTerm.getStartDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .endDate(savedTerm.getEndDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .status(savedTerm.getStatus())
                .createdAt(savedTerm.getCreatedAt())
                .sessionId(savedTerm.getTermId())
                .tenantId(savedTerm.getAdminTenant().getTenantId())
                .message(Utilities.SESSION_UPDATED_SUCCESSFULLY)
                .build();
    }


}
