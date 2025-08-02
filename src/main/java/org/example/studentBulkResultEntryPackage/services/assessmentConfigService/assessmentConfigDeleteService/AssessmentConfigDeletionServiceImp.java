package org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigDeleteService;

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
public class AssessmentConfigDeletionServiceImp implements AssessmentConfigDeletionService {

    private final AssessmentConfigRepository assessmentConfigRepository;
    private final TenantSecurityUtil tenantSecurityUtil;

    @Override
    public void deleteAssessmentConfig(String name) {
        AdminTenant adminTenant = tenantSecurityUtil.getAuthenticatedTenant();
        UUID tenantId = tenantSecurityUtil.getAuthenticatedTenant().getTenantId();


        AssessmentConfig config = assessmentConfigRepository.findByGroupNameAndTenant_TenantId(name, adminTenant.getTenantId())
                .orElseThrow(() -> new NoSuchElementException("Assessment config not found with name: " + name));

        assessmentConfigRepository.delete(config);
    }

}
