package org.example.studentBulkResultEntryPackage.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ScoreEntryDto {
    private UUID examConfigId; // UUID for exam config
    private Double score;
}