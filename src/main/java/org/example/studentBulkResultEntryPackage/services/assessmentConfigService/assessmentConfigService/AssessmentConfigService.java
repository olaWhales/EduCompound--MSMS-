package org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigService;

import org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest.AssessmentConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentGroupResponse;
import org.springframework.transaction.annotation.Transactional;

public interface AssessmentConfigService {
    @Transactional
    AssessmentGroupResponse saveAssessmentConfig(AssessmentConfigRequest assessmentConfigRequest);
}