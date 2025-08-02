package org.example.studentBulkResultEntryPackage.services.gradingConfigService.getAllGradesConfigService;

import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;

import java.util.List;

public interface GetAllGradingConfigService {
    List<GradingConfigResponse> getAll();
}
