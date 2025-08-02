//package org.example.studentBulkResultEntryPackage.services.examConfigService.examConfigCreateService;
//
//import lombok.RequiredArgsConstructor;
//import org.example.data.model.AdminTenant;
//import org.example.data.repositories.AdminTenantRepository;
//import org.example.studentBulkResultEntryPackage.data.model.GradingConfig;
//import org.example.studentBulkResultEntryPackage.data.repository.ExamConfigRepository;
//import org.example.studentBulkResultEntryPackage.services.ConfigValidationUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class ExamConfigServiceImpl implements ExamConfigService {
//    private final ExamConfigRepository examConfigRepository;
//    private final AdminTenantRepository adminTenantRepository;
//
//    private static final int TOTAL_WEIGHT_LIMIT = 100;
//    @Override
//    @Transactional
//    public GradingConfig createExamConfig(GradingConfig config, UUID tenantId) {
//        ConfigValidationUtils.validateTenant(tenantId, adminTenantRepository);
//        ConfigValidationUtils.validateExamConfig(config);
//
//        AdminTenant tenant = adminTenantRepository.findById(tenantId).orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));
//        // Check for duplicate name within tenant
//        if (examConfigRepository.findByNameAndTenant(config.getName(), tenant).isPresent()) {throw new IllegalArgumentException("Exam config with name '" + config.getName() + "' already exists for this tenant");}
//        // Validate total weight
//        validateTotalWeight(config.getWeight(), null, tenant);
//        config.setTenant(tenant);
//        config.setId(null); // Ensure new ID is generated
//        return examConfigRepository.save(config);
//    }
//    private void validateTotalWeight(Double newWeight, Double oldWeight, AdminTenant tenant) {
//        Double currentAssessmentWeight = examConfigRepository.sumWeightsByTenantAndIsActiveTrue(tenant);
//        Double currentExamWeight = examConfigRepository.sumWeightsByTenantAndIsActiveTrue(tenant);
//
//        double weightAdjustment = oldWeight != null ? newWeight - oldWeight : newWeight;
//        double totalWeight = (currentAssessmentWeight != null ? currentAssessmentWeight : 0.0) + 0.0 + weightAdjustment;
//        if (totalWeight > TOTAL_WEIGHT_LIMIT) {
//            throw new IllegalArgumentException("Total weight of active assessment and exam configs cannot exceed " + TOTAL_WEIGHT_LIMIT);
//        }
//    }
//}