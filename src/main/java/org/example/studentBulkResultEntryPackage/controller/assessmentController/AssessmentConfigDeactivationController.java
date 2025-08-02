package org.example.studentBulkResultEntryPackage.controller.assessmentController;

import lombok.RequiredArgsConstructor;
import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigDeactivationService.AssessmentConfigDeactivationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assessment-configs")
@RequiredArgsConstructor
public class AssessmentConfigDeactivationController {

    private final AssessmentConfigDeactivationService activationService;

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateConfig(@PathVariable String id) {
        activationService.deactivateAssessmentConfig(id);
        return ResponseEntity.ok("Assessment configuration deactivated successfully.");
    }
}
