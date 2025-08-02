package org.example.studentBulkResultEntryPackage.controller.assessmentController;

import lombok.RequiredArgsConstructor;
import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigActivationService.ExamConfigActivationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessment-configs")
@RequiredArgsConstructor
public class AssessmentConfigActivationController {
    private final ExamConfigActivationService activationService;

    @PutMapping("/{id}/activate")
    public ResponseEntity<String> activate(@PathVariable String id) {
        activationService.activateAssessmentConfig(id);
        return ResponseEntity.ok("Assessment config activated successfully");
    }
}
