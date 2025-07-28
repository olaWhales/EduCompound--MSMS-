package org.example.loginPackage.loginService;

import org.example.loginPackage.dto.loginRequest.LoginRequest;
import org.example.loginPackage.dto.loginResponse.LoginResponse;

public interface UserLoginImp {
    LoginResponse login(LoginRequest loginRequest);
}
