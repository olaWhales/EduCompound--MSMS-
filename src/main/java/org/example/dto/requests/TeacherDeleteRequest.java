package org.example.dto.requests;

import lombok.Data;

@Data
public class TeacherDeleteRequest {
    private String email; // Use email as the identifier
}