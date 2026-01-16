package org.example.sessionPackage.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.Authentication.AuthenticatedUserProvider;
import org.example.data.model.AdminTenant;
import org.example.data.model.Session;
import org.example.data.model.Users;
import org.example.data.repositories.SessionRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionDeleteServiceImpl implements SessionDeleteService {

    private final SessionRepository sessionRepository;
    private final AuthenticatedUserProvider authUserProvider;
    private final TenantContextResolver tenantResolver;
    private final SessionUsageChecker sessionUsageChecker;

    @Override
    public void delete(UUID sessionId) {

        Users admin = authUserProvider.getCurrentUser();
        AdminTenant currentTenant = tenantResolver.currentTenant();

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalStateException("Session not found"));

        // 🔐 Tenant isolation
        if (!session.getAdminTenant().equals(currentTenant)) {
            throw new AccessDeniedException("You cannot delete a session from another tenant");
        }

        // 🚫 Prevent deleting already inactive session
        if (!session.isActive()) {
            throw new IllegalStateException("Session is already archived");
        }

        // 🚫 Business rule: prevent deletion if session already used
//        if (session.hasAcademicRecords()) { // <-- boolean method in entity
//            throw new IllegalStateException(
//                    "Cannot delete session with academic records. Archive instead."
//            );
//        }
        if (sessionUsageChecker.hasAcademicRecords(session)) {
            throw new IllegalStateException(
                    "Cannot delete session with academic records. Archive instead."
            );
        }


        // ✅ Soft delete (archive)
        session.setActive(false);
        session.setDeletedAt(Instant.now());
        session.setDeletedBy(admin.getUserId());

        sessionRepository.save(session);
    }
}


