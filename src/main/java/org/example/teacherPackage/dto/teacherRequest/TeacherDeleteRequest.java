package org.example.teacherPackage.dto.teacherRequest;

import lombok.Data;

@Data
public class TeacherDeleteRequest {
    private String email; // Use email as the identifier
}