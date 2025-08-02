//package org.example.studentBulkResultEntryPackage.controller.examController;
//
//import org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest.ConfigurationRequest;
//import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
//import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigUpdateService.AssessmentConfigUpdateService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.NoSuchElementException;
//
//@RestController
//@RequestMapping("/api")
//public class ExamConfigUpdateController {
//    private final AssessmentConfigUpdateService assessmentConfigService ;
//
//    public ExamConfigUpdateController(AssessmentConfigUpdateService assessmentConfigService) {
//        this.assessmentConfigService = assessmentConfigService;
//    }
//
//    // ✅ Update an existing config by UUID
//    @PutMapping("/assessment_active_update-configs/{id}")
//    public ResponseEntity<?> updateAssessmentConfig(@PathVariable("id") String id,
//                                                    @RequestBody ConfigurationRequest request) {
//        try {
//            AssessmentConfigResponse response = assessmentConfigService.updateAssessmentConfig(id, request);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>("Invalid UUID format: " + id, HttpStatus.BAD_REQUEST);
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity<>("Config not found", HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error updating config: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
