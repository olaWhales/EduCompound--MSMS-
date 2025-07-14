package org.example.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SchoolBranchRequest {
    @NotBlank(message = "Branch name is required")
    private String name;
}
