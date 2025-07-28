package org.example.studentBulkResultEntryPackage.services.assessmentConfigService;

import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.dto.request.AssessmentConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.request.AssessmentConfigUpdateRequest;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface AssessmentConfigUpdateService {
//    @Transactional
//    AssessmentConfigResponse assessmentConfigUpdateResponse(AssessmentConfigUpdateRequest assessmentConfigRequest);

//    @Transactional
//    AssessmentConfigResponse assessmentConfigUpdateResponse(UUID id, AssessmentConfigRequest request);

//    List<AssessmentConfigResponse> getAllConfigsForTenant();

    AssessmentConfigResponse updateAssessmentConfig(String id, AssessmentConfigRequest request);
}
