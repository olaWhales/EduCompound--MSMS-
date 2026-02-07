package org.example.sessionPackage.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.Authentication.AuthenticatedUserProvider;
import org.example.data.model.Term;
import org.example.data.model.TermStatus;
import org.example.data.repositories.TermRepository;
import org.example.utilities.Utilities;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TermRestoreServiceImpl implements TermRestoreService {

    private final TermRepository termRepository;
    private final AuthenticatedUserProvider authUserProvider;
    private final TenantContextResolver tenantResolver;

//    @Override
//    public void restore(UUID sessionId) {
//
//        Users admin = authUserProvider.getCurrentUser();
//        UUID currentTenant = tenantResolver.currentTenantId();
//
//        Session session = sessionRepository.findById(sessionId)
//                .orElseThrow(() -> new IllegalStateException(Utilities.SESSION_NOT_FOUND));
//
//        // 🔐 Tenant isolation
//        if (!session.getAdminTenant().getTenantId().equals(currentTenant)) {
//            throw new AccessDeniedException(
//                    Utilities.YOU_CANNOT_RESTORE_A_SESSION_FROM_ANOTHER_TENANT
//            );
//        }
//
//        // 🚫 Only COMPLETED sessions can be restored
//        if (session.getStatus() != SessionStatus.COMPLETED) {
//            throw new IllegalStateException(
//                    Utilities.ONLY_COMPLETED_SESSIONS_CAN_BE_RESTORED
//            );
//        }
//
//        // ✅ Restore lifecycle
//        session.setStatus(SessionStatus.ACTIVE);
//
//        sessionRepository.save(session);
//    }

//    @Override
//    public void restore(UUID sessionId) {
//
//        UUID tenantId = tenantResolver.currentTenantId();
//        Session session = sessionRepository.findById(sessionId)
//                .orElseThrow(() -> new IllegalStateException(Utilities.SESSION_NOT_FOUND));
//
//        if (!session.getAdminTenant().getTenantId().equals(tenantId)) {
//            throw new AccessDeniedException(Utilities.YOU_CANNOT_RESTORE_A_SESSION_FROM_ANOTHER_TENANT);
//        }
//
//        if (session.getStatus() != SessionStatus.COMPLETED) {
//            throw new IllegalStateException("Only completed sessions can be restored");
//        }
//
//        // Date safety
//        if (session.getEndDate().before(new Date())) {
//            throw new IllegalStateException("Cannot restore a session whose end date has passed");
//        }
//
//        session.setStatus(SessionStatus.ACTIVE);
//        sessionRepository.save(session);
//    }
@Override
public void restore(UUID sessionId) {

    UUID tenantId = tenantResolver.currentTenantId();
    Term term = termRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalStateException(Utilities.SESSION_NOT_FOUND));

    if (!term.getAdminTenant().getTenantId().equals(tenantId)) {
        throw new AccessDeniedException(
                Utilities.YOU_CANNOT_RESTORE_A_SESSION_FROM_ANOTHER_TENANT
        );
    }

    if (term.getStatus() != TermStatus.COMPLETED) {
        throw new IllegalStateException("Only completed sessions can be restored");
    }

    // Manual administrative override
    term.setStatus(TermStatus.ACTIVE);

    termRepository.save(term);
}

}
