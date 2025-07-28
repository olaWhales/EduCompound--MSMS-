package org.example.studentBulkResultEntryPackage.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class GradingConfigResponse {
    private UUID id;
    private String grade;
    private Double minScore;
    private Double maxScore;
}