package org.example.teacherPackage.dto.teacherRequest;

import lombok.Data;

@Data
public class PasswordSetupRequest {
    private String email;
    private String password;
}