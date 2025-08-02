package org.example.studentBulkResultEntryPackage.services.gradingConfigService.gradeConfigUpdateService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.studentBulkResultEntryPackage.data.model.GradingConfig;
import org.example.studentBulkResultEntryPackage.data.repository.GradingConfigRepository;
import org.example.studentBulkResultEntryPackage.dto.request.gradingConfigRequest.GradingConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;
import org.example.studentBulkResultEntryPackage.services.gradingConfigService.GradingConfigMapper;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateGradingConfigServiceImpl implements UpdateGradingConfigService {

    private final GradingConfigRepository repository;
    private final TenantSecurityUtil tenantSecurityUtil;
    private final GradingConfigMapper mapper;

//    @Override
//    public GradingConfigResponse updateExamConfig(UUID id, GradingConfigRequest request) {


    @Override
    public GradingConfigResponse update(UUID id, GradingConfigRequest request) {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();

        GradingConfig config = repository.findByIdAndTenant(id, tenant)
                .filter(GradingConfig::getIsActive)
                .orElseThrow(() -> new IllegalArgumentException("Grading config not found or not active"));

        config.setGradeName(request.getGradeName());
        config.setMinScore(request.getMinScore());
        config.setMaxScore(request.getMaxScore());
        config.setRemark(request.getRemark());

        GradingConfig updated = repository.save(config);
        return mapper.toResponse(updated);
    }

}
