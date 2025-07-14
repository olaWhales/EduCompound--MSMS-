package org.example.services.login;

import org.example.dto.requests.LoginRequest;
import org.example.dto.responses.LoginResponse;

public interface UserLoginImp {
    LoginResponse login(LoginRequest loginRequest);
}
