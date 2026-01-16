package org.example.loginPackage.loginService;

import org.example.loginPackage.dto.loginRequest.LoginRequest;
import org.example.loginPackage.dto.loginResponse.LoginResponse;

public interface UserLoginService {
    LoginResponse login(LoginRequest loginRequest);
}
