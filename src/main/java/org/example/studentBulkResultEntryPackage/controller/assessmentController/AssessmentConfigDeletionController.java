package org.example.studentBulkResultEntryPackage.controller.assessmentController;

import lombok.RequiredArgsConstructor;
import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigDeleteService.AssessmentConfigDeletionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AssessmentConfigDeletionController {

    private final AssessmentConfigDeletionService deletionService;

    @DeleteMapping("/assessment-configs/delete/{name}")
    public ResponseEntity<String> deleteAssessmentConfig(@PathVariable String name) {
        deletionService.deleteAssessmentConfig(name);
        return ResponseEntity.ok("Assessment config deleted successfully");
    }
}
