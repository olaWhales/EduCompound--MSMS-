package org.example.studentBulkResultEntryPackage.controller.assessmentController;

import lombok.RequiredArgsConstructor;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentGetByIdConfigService.AssessmentGetByIdConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GetAssessmentConfigByIdController {

    private final AssessmentGetByIdConfigService getByIdService;

    @GetMapping("/getSingle/assessment-configs/{id}")
    public ResponseEntity<AssessmentConfigResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(getByIdService.getById(id));
    }
}
