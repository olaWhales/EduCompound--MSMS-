package org.example.studentBulkResultEntryPackage.services.assessmentConfigService;

import lombok.AllArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.data.repository.AssessmentConfigRepository;
import org.example.studentBulkResultEntryPackage.dto.request.AssessmentConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AssessmentConfigUpdateServiceImp implements AssessmentConfigUpdateService{
    private final AssessmentConfigRepository assessmentConfigRepository;
    private final TenantSecurityUtil tenantSecurityUtil;
    private final AssessmentConfigMapper mapper; // helper class

    @Override
    public AssessmentConfigResponse updateAssessmentConfig(String id, AssessmentConfigRequest request) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid UUID format");
        }
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();
        AssessmentConfig config = assessmentConfigRepository.findByIdAndTenant(uuid, tenant).orElseThrow(() -> new NoSuchElementException("Config not found for this tenant"));
        config.setName(request.getName());
        config.setWeight(request.getWeight());
        config.setIsRequired(request.getIsRequired());
        config.setIsActive(request.getIsActive());
        assessmentConfigRepository.save(config);
        return mapper.toResponse(config);
    }

//    @Override
//    public List<AssessmentConfigResponse> getAllConfigsForTenant() {
//        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();
//
//        return assessmentConfigRepository.findByTenant(tenant).stream()
//                .map(assessmentConfigMapper::toResponse)
//                .collect(Collectors.toList());
//    }

//    private AssessmentConfigResponse mapToResponse(AssessmentConfig config) {
//        return new AssessmentConfigResponse(
//                config.getId(),
//                config.getName(),
//                config.getWeight(),
//                config.getIsRequired(),
//                config.getIsActive()
//        );
//    }
}
