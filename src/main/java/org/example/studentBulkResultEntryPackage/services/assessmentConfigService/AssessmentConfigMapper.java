package org.example.studentBulkResultEntryPackage.services.assessmentConfigService;

import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentConfigResponse;

public class AssessmentConfigMapper {
    public static AssessmentConfigResponse toResponse(AssessmentConfig cfg) {
        return AssessmentConfigResponse.builder()
                .id(cfg.getId())
                .name(cfg.getName())
                .type(cfg.getType())
                .weight(cfg.getWeight())
                .isRequired(cfg.getIsRequired())
                .isActive(cfg.getIsActive())
                .build();
    }
}
