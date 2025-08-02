package org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentGetAllConfigService;

import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;

import java.util.List;

public interface GetAllAssessmentConfigService{
    List<AssessmentConfigResponse> getAll();
}
