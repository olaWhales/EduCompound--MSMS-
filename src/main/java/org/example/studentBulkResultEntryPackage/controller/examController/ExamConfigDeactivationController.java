//package org.example.studentBulkResultEntryPackage.controller.examController;
//
//import lombok.RequiredArgsConstructor;
//import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigDeactivationService.AssessmentConfigDeactivationService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/assessment-configs")
//@RequiredArgsConstructor
//public class ExamConfigDeactivationController {
//
//    private final AssessmentConfigDeactivationService activationService;
//
//    @PutMapping("/{id}/deactivate")
//    public ResponseEntity<String> deactivateConfig(@PathVariable String id) {
//        activationService.deactivateAssessmentConfig(id);
//        return ResponseEntity.ok("Assessment configuration deactivated successfully.");
//    }
//}
