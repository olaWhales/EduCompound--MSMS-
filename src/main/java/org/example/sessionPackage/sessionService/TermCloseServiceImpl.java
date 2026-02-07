package org.example.sessionPackage.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.Authentication.AuthenticatedUserProvider;
import org.example.data.model.*;
import org.example.data.repositories.TermRepository;
import org.example.utilities.Utilities;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TermCloseServiceImpl implements TermCloseService {

    private final TermRepository termRepository;
    private final TenantContextResolver tenantResolver;
    private final AuthenticatedUserProvider authUserProvider;

    @Override
    public void close(UUID termId) {

        Users admin = authUserProvider.getCurrentUser();
        UUID tenantId = tenantResolver.currentTenantId();

        Term term = termRepository.findById(termId)
                .orElseThrow(() ->
                        new IllegalStateException(Utilities.SESSION_NOT_FOUND)
                );

        // 🔐 Tenant isolation
        if (!term.getAdminTenant().getTenantId().equals(tenantId)) {
            throw new AccessDeniedException(
                    "You cannot close a term from another tenant"
            );
        }

        // 🚫 Only ACTIVE terms can be closed
        if (term.getStatus() != TermStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Only ACTIVE terms can be closed"
            );
        }

        // 🔒 Close term
        term.setStatus(TermStatus.COMPLETED);
        term.setClosedAt(Instant.now());
        term.setClosedBy(admin.getUserId());

        termRepository.save(term);
    }
}
