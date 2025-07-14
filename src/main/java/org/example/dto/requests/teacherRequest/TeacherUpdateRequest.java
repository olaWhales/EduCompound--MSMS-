package org.example.dto.requests.teacherRequest;

import lombok.Data;

@Data
public class TeacherUpdateRequest {
    private String email;
    private String firstName;
    private String lastName;
}