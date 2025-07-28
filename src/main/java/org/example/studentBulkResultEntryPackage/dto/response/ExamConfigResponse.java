package org.example.studentBulkResultEntryPackage.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ExamConfigResponse {
    private UUID id;
    private String name;
    private Integer weight;
    private Boolean isRequired;
}