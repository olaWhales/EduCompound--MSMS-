package org.example.subjectPackage.dto.SubjectRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRequestDTO {
    private String name;
    private String description;
    private List<String> classNames; // instead of classRoomIds
}


