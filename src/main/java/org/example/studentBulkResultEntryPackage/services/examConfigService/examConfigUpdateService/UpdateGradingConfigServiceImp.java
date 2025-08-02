//package org.example.studentBulkResultEntryPackage.services.examConfigService.examConfigUpdateService;
//
//import lombok.AllArgsConstructor;
//import org.example.data.model.AdminTenant;
//import org.example.studentBulkResultEntryPackage.data.model.ExamConfig;
//import org.example.studentBulkResultEntryPackage.data.model.GradeConfig;
//import org.example.studentBulkResultEntryPackage.data.repository.ExamConfigRepository;
//import org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest.ConfigurationRequest;
//import org.example.studentBulkResultEntryPackage.services.UUIDUtil;
//import org.example.utilities.TenantSecurityUtil;
//import org.springframework.stereotype.Service;
//
//import java.util.NoSuchElementException;
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//public class ExamConfigUpdateServiceImp implements ExamConfigUpdateService {
//    private final ExamConfigRepository examConfigRepository;
//    private final TenantSecurityUtil tenantSecurityUtil;
//    private final ExamConfigMapper mapper; // helper class
//
//    @Override
//    public ConfigurationResponse updateExamConfig(String id, ConfigurationRequest request) {
//        UUID uuid = UUIDUtil.validateAndConvert(id); // 👈 No duplication
//        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();
//        ExamConfig config = examConfigRepository.findByIdAndTenant(uuid, tenant).orElseThrow(() -> new NoSuchElementException("Config not found for this tenant"));
//        config.setName(request.getName());
//        config.setWeight(request.getWeight());
//        config.setIsRequired(request.getIsRequired());
////        config.setIsActive(request.getIsActive());
//        examConfigRepository.save(config);
//        return mapper.toResponse(config);
//    }
//}
