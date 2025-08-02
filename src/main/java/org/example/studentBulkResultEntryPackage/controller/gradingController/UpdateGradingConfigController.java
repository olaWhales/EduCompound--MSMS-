package org.example.studentBulkResultEntryPackage.controller.gradingController;

import lombok.RequiredArgsConstructor;
import org.example.studentBulkResultEntryPackage.dto.request.gradingConfigRequest.GradingConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;
import org.example.studentBulkResultEntryPackage.services.gradingConfigService.gradeConfigUpdateService.UpdateGradingConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UpdateGradingConfigController {

    private final UpdateGradingConfigService updateService;

    @PutMapping("/update/grading-config/{id}")
    public ResponseEntity<GradingConfigResponse> updateGrading(
            @PathVariable UUID id,
            @Valid @RequestBody GradingConfigRequest request) {
        return ResponseEntity.ok(updateService.update(id, request));
    }
}
