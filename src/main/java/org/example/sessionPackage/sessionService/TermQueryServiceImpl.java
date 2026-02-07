package org.example.sessionPackage.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.Term;
import org.example.data.repositories.TermRepository;
import org.example.sessionPackage.TermMapper;
import org.example.sessionPackage.dto.sessionResponse.SessionDTO;
import org.example.utilities.Utilities;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TermQueryServiceImpl implements TermQueryService {
    private final TermRepository termRepository;
    private final TenantContextResolver tenantResolver;
    private final TermMapper termMapper;

//    @Override
//    public List<SessionDTO> findAll() {
//        return sessionRepository.findByAdminTenant_TenantId(tenantResolver.currentTenantId())
//                .stream()
//                .map(SessionMapper.INSTANCE::toDTO)
//                .toList();
//    }

    @Override
    public List<SessionDTO> findAll() {
        return termRepository.findByAdminTenant_TenantId(tenantResolver.currentTenantId())
                .stream()
                .map(termMapper::toDTO)
                .toList();
    }

    @Override
    public Term findById(UUID sessionId) {
        return termRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException(Utilities.SESSION_NOT_FOUND));
    }
}


