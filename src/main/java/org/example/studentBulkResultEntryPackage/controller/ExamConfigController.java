package org.example.studentBulkResultEntryPackage.controller;

import org.example.studentBulkResultEntryPackage.data.model.ExamConfig;
import org.example.studentBulkResultEntryPackage.services.examConfigService.ExamConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exam-configs")
public class ExamConfigController {

    private final ExamConfigService examConfigService;

    @Autowired
    public ExamConfigController(ExamConfigService examConfigService) {
        this.examConfigService = examConfigService;
    }

    @PostMapping("/{tenantId}")
    public ResponseEntity<ExamConfig> createExamConfig(
            @PathVariable UUID tenantId,
            @RequestBody ExamConfig config) {
        ExamConfig savedConfig = examConfigService.createExamConfig(config, tenantId);
        return ResponseEntity.ok(savedConfig);
    }

    @GetMapping("/{tenantId}/active")
    public ResponseEntity<List<ExamConfig>> getActiveExamConfigs(
            @PathVariable UUID tenantId) {
        List<ExamConfig> configs = examConfigService.getActiveExamConfigs(tenantId);
        return ResponseEntity.ok(configs);
    }

    @PutMapping("/{tenantId}/{configId}")
    public ResponseEntity<ExamConfig> updateExamConfig(
            @PathVariable UUID tenantId,
            @PathVariable UUID configId,
            @RequestBody ExamConfig config) {
        ExamConfig updatedConfig = examConfigService.updateExamConfig(configId, config, tenantId);
        return ResponseEntity.ok(updatedConfig);
    }
}