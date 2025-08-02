package org.example.studentBulkResultEntryPackage.services.gradingConfigService.gradeConfigUpdateService;

import org.example.studentBulkResultEntryPackage.dto.request.gradingConfigRequest.GradingConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;

import java.util.UUID;

public interface UpdateGradingConfigService {
    GradingConfigResponse update(UUID id, GradingConfigRequest request);
}
