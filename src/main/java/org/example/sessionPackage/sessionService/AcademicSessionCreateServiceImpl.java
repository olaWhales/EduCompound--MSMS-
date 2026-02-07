package org.example.sessionPackage.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.AcademicSessionEntity;
import org.example.data.model.AdminTenant;
import org.example.data.repositories.AcademicSessionRepository;
import org.example.data.repositories.AdminTenantRepository;
import org.example.sessionPackage.dto.sessionRequest.AcademicSessionCreateRequest;
import org.example.sessionPackage.dto.sessionResponse.AcademicSessionResponse;
import org.example.utilities.Utilities;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AcademicSessionCreateServiceImpl implements AcademicSessionCreateService {

    private final AcademicSessionRepository academicSessionRepository;
    private final TenantContextResolver tenantResolver;
    private final AdminTenantRepository adminTenantRepository;


    @Override
    public AcademicSessionResponse create(
            AcademicSessionCreateRequest request
    ) {

        UUID tenantId = tenantResolver.currentTenantId();
        AdminTenant tenant = adminTenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalStateException("Tenant not found"));


        boolean exists =
                academicSessionRepository
                        .existsByTenant_TenantIdAndSessionYear(
                                tenantId,
                                request.getSessionYear()
                        );

        if (exists) {
            throw new IllegalStateException(
                    Utilities.ACADEMIC_SESSION_ALREADY_EXISTS
            );
        }

        AcademicSessionEntity session =
                AcademicSessionEntity.builder()
                        .tenant(tenant)
                        .sessionYear(request.getSessionYear())
                        .build();

        AcademicSessionEntity saved =
                academicSessionRepository.save(session);

        return AcademicSessionResponse.builder()
                .academicSessionId(saved.getId())
                .sessionYear(saved.getSessionYear())
                .tenantId(tenant.getTenantId())
                .message(Utilities.ACADEMIC_SESSION_CREATED_SUCCESSFULLY)
                .build();
    }
}
