package org.example.studentBulkResultEntryPackage.dto.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class StudentResultEntryRequest {
    private UUID studentId;
    private UUID subjectId;
    private UUID classId;
    private UUID sessionId;
    private String term;
    private List<AssessmentScoreDTO> assessments;
    private List<ScoreEntryDto> examAssessments; // New field for exam components
}
