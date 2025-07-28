package org.example.studentBulkResultEntryPackage.services.assessmentConfigService;

import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.dto.request.AssessmentConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AssessmentConfigService {
    @Transactional
    AssessmentConfigResponse assessmentConfigResponse(AssessmentConfigRequest assessmentConfigRequest);

//    @Transactional(readOnly = true)
//    List<AssessmentConfig> getActiveAssessmentConfigs();

//    @Transactional
//    AssessmentConfig updateAssessmentConfig(UUID configId, AssessmentConfig config);
//    List<AssessmentConfig> getActiveConfigsForTenant(UUID tenantId);
//    List<ExamConfig> getActiveExamConfigsForTenant(UUID tenantId);
//    List<GradingConfig> getActiveGradingConfigsForTenant(UUID tenantId);
}