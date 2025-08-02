package org.example.studentBulkResultEntryPackage.services.gradingConfigService.getAllGradesConfigService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.studentBulkResultEntryPackage.data.repository.GradingConfigRepository;
import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;
import org.example.studentBulkResultEntryPackage.services.gradingConfigService.GradingConfigMapper;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllGradingConfigServiceImpl implements GetAllGradingConfigService {

    private final GradingConfigRepository repository;
    private final TenantSecurityUtil tenantSecurityUtil;
    private final GradingConfigMapper mapper;

    @Override
    public List<GradingConfigResponse> getAll() {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();
        return repository.findAllByTenantAndIsActiveTrue(tenant).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
