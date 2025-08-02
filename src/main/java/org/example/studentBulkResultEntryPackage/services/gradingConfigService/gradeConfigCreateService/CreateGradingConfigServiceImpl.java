package org.example.studentBulkResultEntryPackage.services.gradingConfigService.gradeConfigCreateService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.studentBulkResultEntryPackage.data.model.GradingConfig;
import org.example.studentBulkResultEntryPackage.data.repository.GradingConfigRepository;
import org.example.studentBulkResultEntryPackage.dto.request.gradingConfigRequest.GradingConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;
import org.example.studentBulkResultEntryPackage.services.gradingConfigService.GradingConfigMapper;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CreateGradingConfigServiceImpl implements CreateGradingConfigService {

    private final GradingConfigRepository repository;
    private final TenantSecurityUtil tenantSecurityUtil;
    private final GradingConfigMapper mapper;


    @Override
    public GradingConfigResponse createExamConfig(GradingConfigRequest request) {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();

        GradingConfig config = new GradingConfig();
        config.setGradeName(request.getGradeName());
        config.setMinScore(request.getMinScore());
        config.setMaxScore(request.getMaxScore());
        config.setRemark(request.getRemark());
        config.setTenant(tenant);
        config.setIsActive(true);

        GradingConfig saved = repository.save(config);
        return mapper.toResponse(saved);
    }

}