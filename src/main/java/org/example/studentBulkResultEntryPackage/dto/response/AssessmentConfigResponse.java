package org.example.studentBulkResultEntryPackage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentType;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentConfigResponse {
    private UUID id; // Added ID
    private String name;
    private AssessmentType type;
    //    private int numberOfCAs; // e.g., 1, 2, or 3
//    private int numberOfExam ;
    private Integer weight;
    private Boolean isRequired;
    private boolean isActive;

//    public AssessmentConfigResponse(UUID id, String name, int numberOfCAs, int numberOfExam, Integer weight, boolean isRequired) {
//        this.id = id;
//        this.name = name;
//        this.numberOfCAs = numberOfCAs;
//        this.numberOfExam = numberOfExam;
//        this.weight = weight;
//        this.isRequired = isRequired;
////        this.isActive = isActive;
//    }
}