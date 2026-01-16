package org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.repositories.AdminTenantRepository;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.data.repository.AssessmentConfigRepository;
import org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest.AssessmentConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest.ConfigurationRequest;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentGroupResponse;
import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.AssessmentConfigValidator;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentConfigServiceImpl implements AssessmentConfigService {

    private final AssessmentConfigRepository assessmentConfigRepository;
    private final AdminTenantRepository adminTenantRepository;
    private final TenantSecurityUtil tenantSecurityUtil;

    @Transactional
    @Override
    public AssessmentGroupResponse saveAssessmentConfig(AssessmentConfigRequest request) {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();
        if (assessmentConfigRepository.existsByGroupNameAndTenant(request.getName(), tenant)) {throw new IllegalArgumentException("Assessment config group '" + request.getName() + "' already exists.");}
        AssessmentConfigValidator.validate(request);

        List<AssessmentConfig> configs = request.getConfigurations().stream()
            .map(cfg -> AssessmentConfig.builder()
                .name(cfg.getName())
                .type(cfg.getType())
                .weight(cfg.getWeight())
                .isRequired(cfg.getIsRequired())
                .isActive(cfg.getIsActive())
                .tenant(tenant)
                .groupName(request.getName())
                // === ADD THESE TWO LINES ===
                .numberOfCAs(request.getNumberOfCAs())
                .numberOfExam(request.getNumberOfExams())
                // ===========================
                .build())
            .toList();
        List<AssessmentConfig> savedConfigs = assessmentConfigRepository.saveAll(configs);

        List<AssessmentConfigResponse> responses = savedConfigs.stream()
            .map(cfg -> AssessmentConfigResponse.builder()
                .id(cfg.getId())
                .name(cfg.getName())
                .type(cfg.getType())
                .weight(cfg.getWeight())
                .isRequired(cfg.getIsRequired())
                .isActive(cfg.getIsActive())
                .build())
            .toList();

        return AssessmentGroupResponse.builder()
                .name(request.getName())
                .numberOfCAs(request.getNumberOfCAs())
                .numberOfExams(request.getNumberOfExams())
                .assessments(responses)
                .build();
    }
}
