    package org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigDeactivationService;

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
    public class AssessmentConfigDeactivationServiceImp implements AssessmentConfigDeactivationService {
        private final AssessmentConfigRepository assessmentConfigRepository;
        private final TenantSecurityUtil tenantSecurityUtil;

        @Override
        public void deactivateAssessmentConfig(String id) {
            UUID uuid = UUIDUtil.validateAndConvert(id); // 👈 No duplication
            AdminTenant adminTenant = tenantSecurityUtil.getAuthenticatedTenant();
            AssessmentConfig config = assessmentConfigRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("Assessment config not found with ID: " + id));
            // ✅ Ensure the config belongs to the authenticated tenant
            if (!config.getTenant().getTenantId().equals(adminTenant.getTenantId())) {throw new IllegalArgumentException("You are not authorized to deactivate this assessment config");}
            config.setIsActive(false); // Soft delete
            assessmentConfigRepository.save(config);
        }
    }
    // This class i am going to use embedded deactivation in the frontend button