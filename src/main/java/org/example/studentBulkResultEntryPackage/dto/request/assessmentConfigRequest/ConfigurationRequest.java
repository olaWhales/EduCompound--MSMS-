package org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest;

import lombok.Data;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentType;

@Data
public class ConfigurationRequest {
    private String name;
    private Integer weight ;
//    private int numberOfCAs ;
//    private int numberOfExam ;
    private AssessmentType type ;
    private Boolean isRequired;
    private Boolean isActive ;
}
