package org.example.studentBulkResultEntryPackage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentConfigResponse {
    private UUID id; // Added ID
    private String name;
    private Integer weight;
    private Boolean isRequired;
    private Boolean isActive ;
//    private LocalDateTime createdAt ;


    public AssessmentConfigResponse(UUID id, String name, Integer weight, boolean isRequired, boolean isActive) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.isRequired = isRequired;
        this.isActive = isActive;
    }

}