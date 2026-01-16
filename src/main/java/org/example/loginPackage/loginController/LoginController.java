package org.example.loginPackage.loginController;

import lombok.RequiredArgsConstructor;
import org.example.loginPackage.dto.loginRequest.LoginRequest;
import org.example.loginPackage.dto.loginResponse.LoginResponse;
import org.example.loginPackage.loginService.UserLoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {

    private final UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(userLoginService.login(loginRequest));
    }
}
