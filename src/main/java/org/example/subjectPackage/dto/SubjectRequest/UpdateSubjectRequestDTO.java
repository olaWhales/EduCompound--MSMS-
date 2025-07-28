package org.example.subjectPackage.dto.SubjectRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSubjectRequestDTO {

    @NotBlank(message = "Subject name is required")
    private String subjectName;
    private String subjectDescription;
}
