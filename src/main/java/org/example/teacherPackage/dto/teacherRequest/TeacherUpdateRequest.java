package org.example.teacherPackage.dto.teacherRequest;

import lombok.Data;

@Data
public class TeacherUpdateRequest {
    private String email;
    private String firstName;
    private String lastName;
}