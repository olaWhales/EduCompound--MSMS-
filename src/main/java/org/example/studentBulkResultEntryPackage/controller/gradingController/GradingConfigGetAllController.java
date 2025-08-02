package org.example.studentBulkResultEntryPackage.controller.gradingController;

import lombok.RequiredArgsConstructor;
import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;
import org.example.studentBulkResultEntryPackage.services.gradingConfigService.getAllGradesConfigService.GetAllGradingConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GradingConfigGetAllController {
    private final GetAllGradingConfigService getAllGradingConfigService;

    @GetMapping("/getAll/grading-configs")
    public ResponseEntity<List<GradingConfigResponse>> getAll() {
        List<GradingConfigResponse> configs = getAllGradingConfigService.getAll();
        return ResponseEntity.ok(configs);
    }
}
