package org.example.studentBulkResultEntryPackage.controller.assessmentController;

import lombok.RequiredArgsConstructor;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentGetAllConfigService.GetAllAssessmentConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GetAllAssessmentConfigController {

    private final GetAllAssessmentConfigService getAllService;

    @GetMapping("/getAll/assessment-configs")
    public ResponseEntity<List<AssessmentConfigResponse>> getAll() {
        return ResponseEntity.ok(getAllService.getAll());
    }
}
