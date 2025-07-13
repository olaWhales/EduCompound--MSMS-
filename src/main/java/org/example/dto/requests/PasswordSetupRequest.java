package org.example.dto.requests;

import lombok.Data;

@Data
public class PasswordSetupRequest {
    private String email;
    private String password;
}