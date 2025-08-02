package org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentGetByIdConfigService;

import lombok.AllArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.data.repository.AssessmentConfigRepository;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
//import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.AssessmentConfigMapper;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AssessmentGetByIdConfigServiceImp implements AssessmentGetByIdConfigService{
    private final AssessmentConfigRepository repository;
    private final TenantSecurityUtil tenantSecurityUtil;
//    private final AssessmentConfigMapper mapper;

    @Override
    public AssessmentConfigResponse getById(UUID id) {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();
        AssessmentConfig config = repository.findByIdAndTenantAndIsActiveTrue(id, tenant)
                .orElseThrow(() -> new RuntimeException("Assessment Config not found"));
        return AssessmentConfigResponse.builder()
                .id(config.getId())
                .name(config.getName())
                .weight(config.getWeight())
                .type(config.getType())
                .isRequired(config.getIsRequired())
                .isActive(config.getIsActive())
                .build();
    }
}
