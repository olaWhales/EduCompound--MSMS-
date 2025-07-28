package org.example.studentBulkResultEntryPackage.dto.request;

import lombok.Data;

@Data
public class AssessmentConfigRequest {
    private String  name;
    private Integer weight ;
    private Boolean isRequired;
    private Boolean isActive ;
}
