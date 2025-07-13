package org.example.dto.requests;

import lombok.Data;

@Data
public class TeacherUpdateRequest {
    private String email;
    private String firstName;
    private String lastName;
}