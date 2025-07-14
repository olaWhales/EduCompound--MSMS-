package org.example.dto.requests.teacherRequest;

import lombok.Data;

@Data
public class TeacherDeleteRequest {
    private String email; // Use email as the identifier
}