package org.example.studentBulkResultEntryPackage.services.assessmentConfigService;

import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;
import org.springframework.stereotype.Component;

@Component
public class AssessmentConfigMapper {

    public AssessmentConfigResponse toResponse(AssessmentConfig config) {
        return new AssessmentConfigResponse(
                config.getId(),
                config.getName(),
                config.getWeight(),
                config.getIsRequired(),
                config.getIsActive()
        );
    }
}
