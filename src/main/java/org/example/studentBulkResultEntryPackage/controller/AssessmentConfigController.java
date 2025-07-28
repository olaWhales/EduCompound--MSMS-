package org.example.studentBulkResultEntryPackage.controller;

import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.dto.request.AssessmentConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.AssessmentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AssessmentConfigController {

    private final AssessmentConfigService assessmentConfigService;

    @Autowired
    public AssessmentConfigController(AssessmentConfigService assessmentConfigService) {
        this.assessmentConfigService = assessmentConfigService;
    }

    @PostMapping("/assessment-configs/")
    public ResponseEntity<?> createAssessmentConfig(@RequestBody AssessmentConfigRequest config) {
        try {
            return new ResponseEntity<>(assessmentConfigService.assessmentConfigResponse(config), HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


//    // ✅ Get all configs for the current tenant
//    @GetMapping("/")
//    public ResponseEntity<?> getAllAssessmentConfigs() {
//        try {
//            List<AssessmentConfigResponse> configs = assessmentConfigService.getAllConfigsForTenant();
//            return new ResponseEntity<>(configs, HttpStatus.OK);
//        } catch (Exception exception) {
//            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    //    // ✅ Update an existing config by UUID
    //    @PutMapping("/{id}")
    //    public ResponseEntity<?> updateAssessmentConfig(
    //            @PathVariable("id") String id,
    //            @RequestBody AssessmentConfigRequest request
    //    ) {
    //        try {
    //            AssessmentConfigResponse updated = assessmentConfigService.updateAssessmentConfig(id, request);
    //            return new ResponseEntity<>(updated, HttpStatus.OK);
    //        } catch (Exception exception) {
    //            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    //        }
    //    }
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }
}