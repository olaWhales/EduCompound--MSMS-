package org.example.studentBulkResultEntryPackage.services.assessmentConfigService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.repositories.AdminTenantRepository;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.data.repository.AssessmentConfigRepository;
import org.example.studentBulkResultEntryPackage.dto.request.AssessmentConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
import org.example.studentBulkResultEntryPackage.services.ConfigValidationUtils;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssessmentConfigServiceImpl implements AssessmentConfigService {
    private final AssessmentConfigRepository assessmentConfigRepository;
    private final AdminTenantRepository adminTenantRepository;
    private final TenantSecurityUtil tenantSecurityUtil;
    private final AssessmentConfigMapper mapper; // helper class


    @Transactional
    @Override
    public AssessmentConfigResponse assessmentConfigResponse(AssessmentConfigRequest assessmentConfigRequest) {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();
        if (assessmentConfigRepository.findByNameAndTenant(assessmentConfigRequest.getName(), (tenant)).isPresent()){throw new IllegalArgumentException("Assessment config with name '" + assessmentConfigRequest.getName() + "' already exists for this tenant");}
        AssessmentConfig config = AssessmentConfig.builder()
                .name(assessmentConfigRequest.getName())
                .weight(assessmentConfigRequest.getWeight())
                .isActive(assessmentConfigRequest.getIsActive())
                .isRequired(assessmentConfigRequest.getIsRequired())
                .tenant(tenant)
                .build();
       assessmentConfigRepository.save(config);

       return mapper.toResponse(config);
//       AssessmentConfigResponse.builder()
//               .name(requestToResponse.getName())
//               .weight(requestToResponse.getWeight())
//               .isRequired(requestToResponse.getIsRequired())
//               .isActive(requestToResponse.getIsActive())
//               .createdAt(LocalDateTime.now())
//               .build();
    }

//    @Transactional(readOnly = true)
//    @Override
//    public List<AssessmentConfig> getActiveAssessmentConfigs() {
////        ConfigValidationUtils.validateTenant(tenantId, adminTenantRepository);
//        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();
////        AdminTenant tenant = adminTenantRepository.findById(tenantId)
////                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));
//        return assessmentConfigRepository.findByTenantAndIsActiveTrue(tenant);
//    }
}