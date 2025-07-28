package org.example.studentBulkResultEntryPackage.services.examConfigService;

import org.example.studentBulkResultEntryPackage.data.model.ExamConfig;

import java.util.List;
import java.util.UUID;

public interface ExamConfigService {
    ExamConfig createExamConfig(ExamConfig config, UUID tenantId);
    List<ExamConfig> getActiveExamConfigs(UUID tenantId);
    ExamConfig updateExamConfig(UUID configId, ExamConfig config, UUID tenantId);
}