//package org.example.studentBulkResultEntryPackage.services.examConfigService.examConfigActivationService;
//
//import lombok.AllArgsConstructor;
//import org.example.data.model.AdminTenant;
//import org.example.studentBulkResultEntryPackage.data.model.ExamConfig;
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
//public class ExamConfigActivationServiceImp implements ExamConfigActivationService {
//    private final ExamConfigRepository examConfigRepository ;
//    private final TenantSecurityUtil tenantSecurityUtil;
//
//    @Override
//    public void activateExamConfig(String id) {
//        UUID uuid = UUIDUtil.validateAndConvert(id); // 👈 No duplication
//        AdminTenant adminTenant = tenantSecurityUtil.getAuthenticatedTenant();
//        ExamConfig config = examConfigRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("Assessment config not found with ID: " + id));
//        if (!config.getTenant().getTenantId().equals(adminTenant.getTenantId())) {throw new IllegalArgumentException("You are not authorized to activate this assessment config");}
//        config.setIsActive(true);
//        examConfigRepository.save(config);
//    }
//}
//// This class i am going to use embedded Activation in the frontend button