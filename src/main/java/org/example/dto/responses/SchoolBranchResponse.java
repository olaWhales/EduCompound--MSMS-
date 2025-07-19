package org.example.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SchoolBranchResponse {
    private UUID id;
    private String name;
    private String message;
}
