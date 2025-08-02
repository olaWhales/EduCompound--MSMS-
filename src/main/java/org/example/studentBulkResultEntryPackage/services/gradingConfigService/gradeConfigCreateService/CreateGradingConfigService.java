package org.example.studentBulkResultEntryPackage.services.gradingConfigService.gradeConfigCreateService;

import org.example.studentBulkResultEntryPackage.dto.request.gradingConfigRequest.GradingConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;

public interface CreateGradingConfigService {
    GradingConfigResponse createExamConfig(GradingConfigRequest config);
}