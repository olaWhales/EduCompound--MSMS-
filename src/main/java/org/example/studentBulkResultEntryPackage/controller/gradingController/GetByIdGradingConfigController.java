package org.example.studentBulkResultEntryPackage.controller.gradingController;

import lombok.RequiredArgsConstructor;
import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;
import org.example.studentBulkResultEntryPackage.services.gradingConfigService.getGradeByIdConfigService.GetGradingConfigByIdService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GetByIdGradingConfigController {
    private final GetGradingConfigByIdService getGradingConfigByIdService;

    @GetMapping("/getById/grading-configs/{id}")
    public GradingConfigResponse getOne(@PathVariable UUID id) {
        return getGradingConfigByIdService.getOne(id);
    }
}
