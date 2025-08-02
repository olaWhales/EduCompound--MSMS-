package org.example.studentBulkResultEntryPackage.controller.assessmentController;

import lombok.RequiredArgsConstructor;
import org.example.studentBulkResultEntryPackage.dto.request.gradingConfigRequest.ConfigurationBulkDeleteRequest;
import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigBulkDeletionService.AssessmentConfigBulkDeletionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AssessmentConfigBulkDeletionController {

    private final AssessmentConfigBulkDeletionService deletionService;

    @DeleteMapping("/assessment-configs/bulk")
    public ResponseEntity<String> deleteConfigs(@RequestBody ConfigurationBulkDeleteRequest request) {
        deletionService.deleteConfigs(request.getIds());
        return ResponseEntity.ok("Selected assessment configs deleted successfully");
    }
}
