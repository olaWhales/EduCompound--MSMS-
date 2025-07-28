package org.example.schoolBranchPackage.data.schoolBranchResponse;

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
