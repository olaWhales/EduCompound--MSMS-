package org.example.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClassRequest {
    @NotBlank(message = "Class name is required")
    private String className;

//    @NotBlank(message = "Session year is required")
//    private String sessionYear;
//
//    @NotBlank(message = "Term is required")
//    private String term;

    private Long branchId; // optional
}
