//package org.example.studentBulkResultEntryPackage.controller.examController;
//
//import org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest.ConfigurationRequest;
//import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigService.AssessmentConfigService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//public class ExamConfigCreationController {
//
//    private final AssessmentConfigService assessmentConfigService;
//
//    @Autowired
//    public ExamConfigCreationController(AssessmentConfigService assessmentConfigService) {
////        this.assessmentConfigService = assessmentConfigService;
//    }
//
//    @PostMapping("/assessment-configs/")
//    public ResponseEntity<?> createAssessmentConfig(@RequestBody ConfigurationRequest config) {
//        try {
//            return new ResponseEntity<>(assessmentConfigService.assessmentConfigResponse(config), HttpStatus.CREATED);
//        } catch (Exception exception) {
//            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
////    // ✅ Get all configs for the current tenant
////    @GetMapping("/")
////    public ResponseEntity<?> getAllAssessmentConfigs() {
////        try {
////            List<AssessmentConfigResponse> configs = assessmentConfigService.getAllConfigsForTenant();
////            return new ResponseEntity<>(configs, HttpStatus.OK);
////        } catch (Exception exception) {
////            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
////        }
////    }
//
//    //    // ✅ Update an existing config by UUID
//    //    @PutMapping("/{id}")
//    //    public ResponseEntity<?> updateAssessmentConfig(
//    //            @PathVariable("id") String id,
//    //            @RequestBody AssessmentConfigRequest request
//    //    ) {
//    //        try {
//    //            AssessmentConfigResponse updated = assessmentConfigService.updateAssessmentConfig(id, request);
//    //            return new ResponseEntity<>(updated, HttpStatus.OK);
//    //        } catch (Exception exception) {
//    //            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
//    //        }
//    //    }
////    @ExceptionHandler(IllegalArgumentException.class)
////    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
////        return ResponseEntity.badRequest().body(ex.getMessage());
////    }
//}