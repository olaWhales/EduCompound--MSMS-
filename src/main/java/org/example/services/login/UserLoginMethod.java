package org.example.services.login;

import lombok.AllArgsConstructor;
import org.example.dto.requests.LoginRequest;
import org.example.dto.responses.LoginResponse;
import org.example.securityConfig.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserLoginMethod implements UserLoginImp {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        if(loginRequest.getEmail() ==  null || loginRequest.getPassword() == null){
            throw new IllegalArgumentException("You must provide a username and a password");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword())
        );
        String token = jwtService.GenerateToken(loginRequest.getEmail());
        return LoginResponse.builder()
                .token(token)
                .message("Success")
                .build();
    }
}
