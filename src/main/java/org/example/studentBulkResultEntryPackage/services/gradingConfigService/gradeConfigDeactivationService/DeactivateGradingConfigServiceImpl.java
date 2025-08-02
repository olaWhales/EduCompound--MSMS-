package org.example.studentBulkResultEntryPackage.services.gradingConfigService.gradeConfigDeactivationService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.studentBulkResultEntryPackage.data.model.GradingConfig;
import org.example.studentBulkResultEntryPackage.data.repository.GradingConfigRepository;
import org.example.studentBulkResultEntryPackage.services.gradingConfigService.gradeConfigDeactivationService.DeactivateGradingConfigService;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeactivateGradingConfigServiceImpl implements DeactivateGradingConfigService {

    private final GradingConfigRepository repository;
    private final TenantSecurityUtil tenantSecurityUtil;

    @Override
    public void deactivate(UUID id) {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();

        GradingConfig config = repository.findByIdAndTenant(id, tenant)
                .orElseThrow(() -> new RuntimeException("Grading config not found for tenant"));

        config.setIsActive(false);
        repository.save(config);
    }
}
