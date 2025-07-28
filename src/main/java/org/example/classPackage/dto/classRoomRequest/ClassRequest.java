package org.example.classPackage.dto.classRoomRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class ClassRequest {
    @NotBlank(message = "Class name is required")
    private String className;

//    @NotBlank(message = "Session year is required")
//    private String sessionYear;
//
//    @NotBlank(message = "Term is required")
//    private String term;

    private UUID branchId; // optional
}
