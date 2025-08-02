//    package org.example.studentBulkResultEntryPackage.services.examConfigService.examConfigDeactivationService;
//
//    import lombok.AllArgsConstructor;
//    import org.example.data.model.AdminTenant;
//    import org.example.studentBulkResultEntryPackage.data.model.ExamConfig;
//    import org.example.studentBulkResultEntryPackage.data.model.GradeConfig;
//    import org.example.studentBulkResultEntryPackage.data.repository.ExamConfigRepository;
//    import org.example.studentBulkResultEntryPackage.services.UUIDUtil;
//    import org.example.utilities.TenantSecurityUtil;
//    import org.springframework.stereotype.Service;
//
//    import java.util.NoSuchElementException;
//    import java.util.UUID;
//
//    @Service
//    @AllArgsConstructor
//    public class ExamConfigDeactivationServiceImp implements ExamConfigDeactivationService {
//        private final ExamConfigRepository examConfigRepository ;
//        private final TenantSecurityUtil tenantSecurityUtil;
//
//        @Override
//        public void deactivateExamConfig(String id) {
//            UUID uuid = UUIDUtil.validateAndConvert(id); // 👈 No duplication
//            AdminTenant adminTenant = tenantSecurityUtil.getAuthenticatedTenant();
//            ExamConfig config = examConfigRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("Assessment config not found with ID: " + id));
//            // ✅ Ensure the config belongs to the authenticated tenant
//            if (!config.getTenant().getTenantId().equals(adminTenant.getTenantId())) {throw new IllegalArgumentException("You are not authorized to deactivate this assessment config");}
//            config.setIsActive(false); // Soft delete
//            examConfigRepository.save(config);
//        }
//    }
//    // This class i am going to use embedded deactivation in the frontend button