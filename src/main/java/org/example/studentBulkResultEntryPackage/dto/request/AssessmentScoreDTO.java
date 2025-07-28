package org.example.studentBulkResultEntryPackage.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class AssessmentScoreDTO {
    private UUID assessmentConfigId; // Changed to UUID
    private Double score;
}