package org.example.loginPackage.dto.loginRequest;

import lombok.*;

@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String emailOrStudentCode;
    private String password;
}

