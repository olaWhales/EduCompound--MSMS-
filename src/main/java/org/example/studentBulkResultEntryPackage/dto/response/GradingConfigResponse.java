package org.example.studentBulkResultEntryPackage.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class GradingConfigResponse {
    private UUID id;
    private String gradeName;
    private Integer minScore;
    private Integer maxScore;
    private String remark;
    private Boolean isActive;
}
