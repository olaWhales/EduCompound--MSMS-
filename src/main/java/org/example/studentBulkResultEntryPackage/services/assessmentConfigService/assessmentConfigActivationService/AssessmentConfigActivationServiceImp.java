package org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigActivationService;

import lombok.AllArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.data.repository.AssessmentConfigRepository;
import org.example.studentBulkResultEntryPackage.services.UUIDUtil;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AssessmentConfigActivationServiceImp implements ExamConfigActivationService {
    private final AssessmentConfigRepository assessmentConfigRepository;
    private final TenantSecurityUtil tenantSecurityUtil;

    @Override
    public void activateAssessmentConfig(String id) {
        UUID uuid = UUIDUtil.validateAndConvert(id); // 👈 No duplication
        AdminTenant adminTenant = tenantSecurityUtil.getAuthenticatedTenant();
        AssessmentConfig config = assessmentConfigRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("Assessment config not found with ID: " + id));
        if (!config.getTenant().getTenantId().equals(adminTenant.getTenantId())) {throw new IllegalArgumentException("You are not authorized to activate this assessment config");}
        config.setIsActive(true);
        assessmentConfigRepository.save(config);
    }
}
// This class i am going to use embedded Activation in the frontend button