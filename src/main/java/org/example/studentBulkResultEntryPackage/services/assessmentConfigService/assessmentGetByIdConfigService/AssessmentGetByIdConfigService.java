package org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentGetByIdConfigService;

import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;

import java.util.UUID;

public interface AssessmentGetByIdConfigService {
    AssessmentConfigResponse getById(UUID id);
}
