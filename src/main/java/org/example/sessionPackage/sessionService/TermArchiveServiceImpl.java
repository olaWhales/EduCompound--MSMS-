package org.example.sessionPackage.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.Authentication.AuthenticatedUserProvider;
import org.example.data.model.Term;
import org.example.data.model.TermStatus;
import org.example.data.model.Users;
import org.example.data.repositories.TermRepository;
import org.example.sessionPackage.dto.sessionResponse.TermDeletionResponse;
import org.example.utilities.Utilities;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TermArchiveServiceImpl implements TermDeleteService {

    private final TermRepository termRepository;
    private final AuthenticatedUserProvider authUserProvider;
    private final TenantContextResolver tenantResolver;
    private final TermUsageChecker termUsageChecker;

    @Override
    public TermDeletionResponse delete(UUID sessionId) {

        Users admin = authUserProvider.getCurrentUser();
        UUID currentTenant = tenantResolver.currentTenantId();
        Term term = termRepository.findById(sessionId).orElseThrow(() -> new IllegalStateException(Utilities.SESSION_NOT_FOUND));
        // 🔐 Tenant isolation
        if (!term.getAdminTenant().getTenantId().equals(currentTenant)) {throw new AccessDeniedException(Utilities.YOU_CANNOT_DELETE_A_SESSION_FROM_ANOTHER_TENANT);}
        // 🚫 Prevent deleting already inactive session

        if (term.getStatus() != TermStatus.PLANNED) {
            throw new IllegalStateException("Only PLANNED sessions can be deleted. Close or archive instead.");
        }

        if (termUsageChecker.hasAcademicRecords(term)) {
            throw new IllegalStateException(Utilities.CANNOT_DELETE_SESSION_WITH_ACADEMIC_RECORDS_ARCHIVE_INSTEAD);
        }

        if (term.getStatus() == TermStatus.ARCHIVED) {throw new IllegalStateException(Utilities.SESSION_IS_ALREADY_ARCHIVED);}

        // ✅ Soft delete (archive)
        term.setStatus(TermStatus.ARCHIVED);
//        term.setActive(false);
        term.setDeletedAt(Instant.now());
        term.setDeletedBy(admin.getUserId());

        termRepository.save(term);

        return TermDeletionResponse.builder()
                .message("Session deleted successfully")
                .build();
    }
}


