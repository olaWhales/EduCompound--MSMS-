package org.example.studentBulkResultEntryPackage.services.gradingConfigService.getGradeByIdConfigService;

import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;

import java.util.UUID;

public interface GetGradingConfigByIdService {
    GradingConfigResponse getOne(UUID id);
}
