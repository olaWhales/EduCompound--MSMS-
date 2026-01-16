package org.example.sessionPackage.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.Session;
import org.example.data.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionQueryServiceImpl implements SessionQueryService {

    private final SessionRepository sessionRepository;
    private final TenantContextResolver tenantResolver;

    @Override
    public List<Session> findAll() {
        return sessionRepository.findByAdminTenant(
                tenantResolver.currentTenant()
        );
    }

    @Override
    public Session findById(UUID sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }
}

