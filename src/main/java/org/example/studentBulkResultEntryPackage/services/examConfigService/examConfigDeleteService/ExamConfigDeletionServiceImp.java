//package org.example.studentBulkResultEntryPackage.services.examConfigService.examConfigDeleteService;
//
//import lombok.AllArgsConstructor;
//import org.example.data.model.AdminTenant;
//import org.example.studentBulkResultEntryPackage.data.model.ExamConfig;
//import org.example.studentBulkResultEntryPackage.data.model.GradeConfig;
//import org.example.studentBulkResultEntryPackage.data.repository.ExamConfigRepository;
//import org.example.studentBulkResultEntryPackage.services.UUIDUtil;
//import org.example.utilities.TenantSecurityUtil;
//import org.springframework.stereotype.Service;
//
//import java.util.NoSuchElementException;
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//public class ExamConfigDeletionServiceImp implements ExamConfigDeletionService {
//
//    private final ExamConfigRepository examConfigRepository;
//    private final TenantSecurityUtil tenantSecurityUtil;
//
//    @Override
//    public void deleteExamConfig(String id) {
//        UUID uuid = UUIDUtil.validateAndConvert(id); // 👈 No duplication
//        AdminTenant adminTenant = tenantSecurityUtil.getAuthenticatedTenant();
//        ExamConfig config = examConfigRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("Assessment config not found with ID: " + id));
//        // 🔐 Ensure ownership before deletion
//        if (!config.getTenant().getTenantId().equals(adminTenant.getTenantId())) {throw new IllegalArgumentException("You are not authorized to delete this assessment config");}
//        examConfigRepository.delete(config);
//    }
//}
