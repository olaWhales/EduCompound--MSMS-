package org.example.studentBulkResultEntryPackage.services.examConfigService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.repositories.AdminTenantRepository;
import org.example.studentBulkResultEntryPackage.data.model.ExamConfig;
import org.example.studentBulkResultEntryPackage.data.repository.AssessmentConfigRepository;
import org.example.studentBulkResultEntryPackage.data.repository.ExamConfigRepository;
import org.example.studentBulkResultEntryPackage.services.ConfigValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamConfigServiceImpl implements ExamConfigService {
    private final ExamConfigRepository examConfigRepository;
    private final AssessmentConfigRepository assessmentConfigRepository;
    private final AdminTenantRepository adminTenantRepository;

    private static final int TOTAL_WEIGHT_LIMIT = 100;

    @Override
    @Transactional
    public ExamConfig createExamConfig(ExamConfig config, UUID tenantId) {
        ConfigValidationUtils.validateTenant(tenantId, adminTenantRepository);
        ConfigValidationUtils.validateExamConfig(config);

        AdminTenant tenant = adminTenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));

        // Check for duplicate name within tenant
        if (examConfigRepository.findByNameAndTenant(config.getName(), tenant).isPresent()) {
            throw new IllegalArgumentException("Exam config with name '" + config.getName() + "' already exists for this tenant");
        }

        // Validate total weight
        validateTotalWeight(config.getWeight(), null, tenant);

        config.setTenant(tenant);
        config.setId(null); // Ensure new ID is generated
        return examConfigRepository.save(config);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamConfig> getActiveExamConfigs(UUID tenantId) {
        ConfigValidationUtils.validateTenant(tenantId, adminTenantRepository);
        AdminTenant tenant = adminTenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));
        return examConfigRepository.findByTenantAndIsActiveTrue(tenant);
    }

    @Override
    @Transactional
    public ExamConfig updateExamConfig(UUID configId, ExamConfig config, UUID tenantId) {
        ConfigValidationUtils.validateTenant(tenantId, adminTenantRepository);
        ConfigValidationUtils.validateExamConfig(config);

        AdminTenant tenant = adminTenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));

        ExamConfig existingConfig = examConfigRepository.findById(configId)
                .orElseThrow(() -> new IllegalArgumentException("Exam config not found with ID: " + configId));

        if (!existingConfig.getTenant().getTenantId().equals(tenantId)) {
            throw new IllegalArgumentException("Exam config does not belong to the specified tenant");
        }

        // Check for duplicate name (excluding current config)
        examConfigRepository.findByNameAndTenantAndIdNot(config.getName(), tenant, configId)
                .ifPresent(c -> {
                    throw new IllegalArgumentException("Exam config with name '" + config.getName() + "' already exists for this tenant");
                });

        // Validate total weight
        validateTotalWeight(config.getWeight(), existingConfig.getWeight(), tenant);

        existingConfig.setName(config.getName());
        existingConfig.setWeight(config.getWeight());
        existingConfig.setIsRequired(config.getIsRequired());
        existingConfig.setIsActive(config.getIsActive());
        return examConfigRepository.save(existingConfig);
    }

    private void validateTotalWeight(Double newWeight, Double oldWeight, AdminTenant tenant) {
        Double currentAssessmentWeight = assessmentConfigRepository.sumWeightsByTenantAndIsActiveTrue(tenant);
        Double currentExamWeight = examConfigRepository.sumWeightsByTenantAndIsActiveTrue(tenant);

        double weightAdjustment = oldWeight != null ? newWeight - oldWeight : newWeight;
        double totalWeight = (currentAssessmentWeight != null ? currentAssessmentWeight : 0.0) +
                0.0 + weightAdjustment;

        if (totalWeight > TOTAL_WEIGHT_LIMIT) {
            throw new IllegalArgumentException("Total weight of active assessment and exam configs cannot exceed " + TOTAL_WEIGHT_LIMIT);
        }
    }
}