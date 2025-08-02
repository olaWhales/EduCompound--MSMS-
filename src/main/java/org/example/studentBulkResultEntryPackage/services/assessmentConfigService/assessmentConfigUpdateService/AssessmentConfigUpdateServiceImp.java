package org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigUpdateService;

import lombok.AllArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentType;
import org.example.studentBulkResultEntryPackage.data.repository.AssessmentConfigRepository;
import org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest.AssessmentConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest.ConfigurationRequest;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
//import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.AssessmentConfigMapper;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentGroupResponse;
import org.example.studentBulkResultEntryPackage.services.UUIDUtil;
import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.AssessmentConfigMapper;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
@Service
@AllArgsConstructor
public class AssessmentConfigUpdateServiceImp implements AssessmentConfigUpdateService {

    private final AssessmentConfigRepository assessmentConfigRepository;
    private final TenantSecurityUtil tenantSecurityUtil;

    @Override
    public AssessmentGroupResponse updateAssessmentConfig(String groupName, AssessmentConfigRequest request) {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();

        // Step 1: Fetch existing configs by group name
        List<AssessmentConfig> existingConfigs = assessmentConfigRepository.findByGroupNameAndTenant(groupName, tenant);
        if (existingConfigs.isEmpty()) {
            throw new NoSuchElementException("No assessment configs found for group: " + groupName);
        }

        // Step 2: Get the names from the incoming request
        List<String> incomingNames = request.getConfigurations().stream()
                .map(ConfigurationRequest::getName)
                .toList();

        // Step 3: Delete configs that are no longer in the request
        List<UUID> toDelete = existingConfigs.stream()
                .filter(cfg -> !incomingNames.contains(cfg.getName()))
                .map(AssessmentConfig::getId)
                .toList();
        assessmentConfigRepository.deleteAllById(toDelete);

        // Step 4: Update existing configs or create new ones
        List<AssessmentConfig> updatedConfigs = request.getConfigurations().stream()
                .map(cfgReq -> {
                    AssessmentConfig existing = existingConfigs.stream()
                            .filter(e -> e.getName().equals(cfgReq.getName()))
                            .findFirst()
                            .orElse(null);

                    if (existing != null) {
                        existing.setType(cfgReq.getType());
                        existing.setWeight(cfgReq.getWeight());
                        existing.setIsRequired(cfgReq.getIsRequired());
                        existing.setIsActive(cfgReq.getIsActive());
                        existing.setGroupName(request.getName());
                        return existing;
                    } else {
                        return AssessmentConfig.builder()
                                .name(cfgReq.getName())
                                .type(cfgReq.getType())
                                .weight(cfgReq.getWeight())
                                .isRequired(cfgReq.getIsRequired())
                                .isActive(cfgReq.getIsActive())
                                .tenant(tenant)
                                .groupName(request.getName())
                                .build();
                    }
                })
                .toList();

        // Step 5: Save all configs
        List<AssessmentConfig> saved = assessmentConfigRepository.saveAll(updatedConfigs);

        // ✅ Step 6: Recalculate numberOfCAs and numberOfExams based on isActive
        long activeCAs = saved.stream()
                .filter(cfg -> cfg.getIsActive() && cfg.getType().equals(AssessmentType.CA))
                .count();

        long activeExams = saved.stream()
                .filter(cfg -> cfg.getIsActive() && cfg.getType().equals(AssessmentType.EXAM))
                .count();

        // Step 7: Map to response DTO
        List<AssessmentConfigResponse> responses = saved.stream()
                .map(cfg -> AssessmentConfigResponse.builder()
                        .id(cfg.getId())
                        .name(cfg.getName())
                        .type(cfg.getType())
                        .weight(cfg.getWeight())
                        .isRequired(cfg.getIsRequired())
                        .isActive(cfg.getIsActive())
                        .build())
                .toList();

        // Step 8: Return group response with recalculated values
        return AssessmentGroupResponse.builder()
                .name(request.getName())
                .numberOfCAs((int) activeCAs)
                .numberOfExams((int) activeExams)
                .assessments(responses)
                .build();
    }


//    @Override
//    public AssessmentGroupResponse updateAssessmentConfig(String groupName, AssessmentConfigRequest request) {
//        return null;
//    }
}
