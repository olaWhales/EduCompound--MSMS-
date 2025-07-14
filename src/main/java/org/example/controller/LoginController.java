package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.requests.LoginRequest;
import org.example.services.login.UserLoginImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class LoginController {
    private final UserLoginImp userLoginImp;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try{
            return new ResponseEntity<>(userLoginImp.login(loginRequest), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
