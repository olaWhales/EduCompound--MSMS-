package org.example.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolBranchResponse {
    private Long id;
    private String name;
    private String message;
}
