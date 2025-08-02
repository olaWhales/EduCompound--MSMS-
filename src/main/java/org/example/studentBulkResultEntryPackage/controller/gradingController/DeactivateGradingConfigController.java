package org.example.studentBulkResultEntryPackage.controller.gradingController;

import lombok.RequiredArgsConstructor;
import org.example.studentBulkResultEntryPackage.services.gradingConfigService.gradeConfigDeactivationService.DeactivateGradingConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeactivateGradingConfigController {

    private final DeactivateGradingConfigService deactivateGradingConfigService;

    @DeleteMapping("/grading-config/deactivate/{id}")
    public ResponseEntity<String> deactivate(@PathVariable UUID id) {
        deactivateGradingConfigService.deactivate(id);
        return ResponseEntity.ok("Grading configuration deactivated successfully.");
    }
}
