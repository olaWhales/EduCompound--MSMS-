package org.example.studentBulkResultEntryPackage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentGroupResponse {
    private String name;
    private int numberOfCAs;
    private int numberOfExams;
    private List<AssessmentConfigResponse> assessments;
}
