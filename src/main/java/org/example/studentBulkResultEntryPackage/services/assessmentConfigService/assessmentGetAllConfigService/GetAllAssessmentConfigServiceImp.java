package org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentGetAllConfigService;

import lombok.AllArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.data.repository.AssessmentConfigRepository;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
//import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.AssessmentConfigMapper;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAllAssessmentConfigServiceImp implements GetAllAssessmentConfigService{
    private final AssessmentConfigRepository repository;
    private final TenantSecurityUtil tenantSecurityUtil;
//    private final AssessmentConfigMapper mapper;

    public List<AssessmentConfigResponse> getAll() {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();
        List<AssessmentConfig> configs = new ArrayList<>(repository.findAllByTenantAndIsActiveTrue(tenant));
//        List<AssessmentConfig> configs = repository.findAllByTenantAndIsActiveTrue(tenant);

        return configs.stream()
                .map(config -> AssessmentConfigResponse.builder()
                        .id(config.getId())
                        .name(config.getName())
                        .type(config.getType())
                        .weight(config.getWeight())
                        .isRequired(config.getIsRequired())
                        .isActive(config.getIsActive())
                        .build())
                .collect(Collectors.toList());
    }
}
