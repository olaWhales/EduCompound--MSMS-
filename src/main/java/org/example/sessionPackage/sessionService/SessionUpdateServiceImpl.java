package org.example.sessionPackage.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.Authentication.AuthenticatedUserProvider;
import org.example.data.model.AdminTenant;
import org.example.data.model.Role;
import org.example.data.model.Session;
import org.example.data.model.Users;
import org.example.data.repositories.SessionRepository;
import org.example.sessionPackage.dto.sessionRequest.SessionRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionUpdateServiceImpl implements SessionUpdateService {

    private final SessionRepository sessionRepository;
    private final TenantContextResolver tenantResolver;
    private final SessionValidator sessionValidator;
    private final AuthenticatedUserProvider authUserProvider;

    @Override
    public void update(UUID sessionId, SessionRequest request) {

        Users admin = authUserProvider.getCurrentUser();
        AdminTenant tenant = admin.getAdminTenant();

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (!session.getAdminTenant().getTenantId().equals(tenant.getTenantId())) {
            throw new AccessDeniedException("Cannot update session from another tenant");
        }

        if (admin.getRole() != Role.ADMIN && !session.isActive()) {
            throw new AccessDeniedException("Only admins can update inactive sessions");
        }

        sessionValidator.validateDates(
                request.getStartDate(),
                request.getEndDate()
        );

        session.setSessionYear(request.getSessionYear());
        session.setTerm(request.getTerm());
        session.setStartDate(request.getStartDate());
        session.setEndDate(request.getEndDate());

        sessionRepository.save(session);
    }
}

