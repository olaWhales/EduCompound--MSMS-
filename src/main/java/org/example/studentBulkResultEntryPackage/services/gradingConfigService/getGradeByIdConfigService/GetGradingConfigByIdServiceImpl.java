// src/main/java/org/example/studentBulkResultEntryPackage/services/gradingConfigService/getGradeByIdConfigService/GetGradingConfigByIdServiceImpl.java
package org.example.studentBulkResultEntryPackage.services.gradingConfigService.getGradeByIdConfigService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.studentBulkResultEntryPackage.data.model.GradingConfig;
import org.example.studentBulkResultEntryPackage.data.repository.GradingConfigRepository;
import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;
import org.example.studentBulkResultEntryPackage.services.gradingConfigService.GradingConfigMapper;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetGradingConfigByIdServiceImpl implements GetGradingConfigByIdService {

    private final GradingConfigRepository repository;
    private final TenantSecurityUtil tenantSecurityUtil;
    private final GradingConfigMapper mapper;

    @Override
    public GradingConfigResponse getOne(UUID id) {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();

        GradingConfig config = repository.findByIdAndTenantAndIsActiveTrue(id, tenant)
                .orElseThrow(() -> new IllegalArgumentException("GradingConfig not found for tenant"));
        return mapper.toResponse(config);
    }
}
