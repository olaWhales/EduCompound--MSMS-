package org.example.studentBulkResultEntryPackage.dto.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class StudentResultDetailsDTO {
    private UUID resultId;
    private List<AssessmentScoreDTO> continuousAssessments;
    private List<AssessmentScoreDTO> examAssessments;
}