//package org.example.studentBulkResultEntryPackage.controller.examController;
//
//import lombok.RequiredArgsConstructor;
//import org.example.studentBulkResultEntryPackage.dto.request.gradingConfigRequest.ConfigurationBulkDeleteRequest;
//import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigBulkDeletionService.AssessmentConfigBulkDeletionService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class ExamConfigBulkDeletionController {
//
//    private final AssessmentConfigBulkDeletionService deletionService;
//
//    @DeleteMapping("/assessment-configs/bulk")
//    public ResponseEntity<String> deleteConfigs(@RequestBody ConfigurationBulkDeleteRequest request) {
//        deletionService.deleteConfigs(request.getIds());
//        return ResponseEntity.ok("Selected assessment configs deleted successfully");
//    }
//}
