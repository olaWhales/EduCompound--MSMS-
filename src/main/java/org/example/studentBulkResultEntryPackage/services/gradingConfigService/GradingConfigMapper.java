package org.example.studentBulkResultEntryPackage.services.gradingConfigService;

import org.example.studentBulkResultEntryPackage.data.model.GradingConfig;
import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;
import org.springframework.stereotype.Component;

@Component
public class GradingConfigMapper {

    public GradingConfigResponse toResponse(GradingConfig config) {
        GradingConfigResponse res = new GradingConfigResponse();
        res.setId(config.getId());
        res.setGradeName(config.getGradeName());
        res.setMinScore(config.getMinScore());
        res.setMaxScore(config.getMaxScore());
        res.setRemark(config.getRemark());
        res.setIsActive(config.getIsActive());
        return res;
    }
}

