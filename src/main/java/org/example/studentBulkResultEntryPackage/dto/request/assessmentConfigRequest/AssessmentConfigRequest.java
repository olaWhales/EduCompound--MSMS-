package org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest;

import lombok.Data;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentType;

import java.util.List;

@Data
public class AssessmentConfigRequest {
    private String name;
    private int numberOfCAs; // Defined once for the whole grading config
    private int numberOfExams;    // Typically 1, but defined explicitly for clarity
    private AssessmentType type;
    private List<ConfigurationRequest> configurations; // e.g., 1st CA, 2nd CA, Exam
}
