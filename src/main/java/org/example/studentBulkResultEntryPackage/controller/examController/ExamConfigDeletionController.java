//package org.example.studentBulkResultEntryPackage.controller.examController;
//
//import lombok.RequiredArgsConstructor;
//import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigDeleteService.AssessmentConfigDeletionService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/assessment-configs")
//@RequiredArgsConstructor
//public class ExamConfigDeletionController {
//
//    private final AssessmentConfigDeletionService deletionService;
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteAssessmentConfig(@PathVariable String id) {
//        deletionService.deleteAssessmentConfig(id);
//        return ResponseEntity.ok("Assessment config deleted successfully");
//    }
//}
