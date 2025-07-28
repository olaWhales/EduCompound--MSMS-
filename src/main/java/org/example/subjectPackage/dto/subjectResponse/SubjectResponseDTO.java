package org.example.subjectPackage.dto.subjectResponse;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private List<String> classNames;
}
