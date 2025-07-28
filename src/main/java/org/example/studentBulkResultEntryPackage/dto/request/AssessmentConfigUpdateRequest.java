package org.example.studentBulkResultEntryPackage.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class AssessmentConfigUpdateRequest {
    private UUID id; // used internally by frontend
    private String name;
    private double weight;
    private boolean isRequired;
    private boolean isActive;
}
