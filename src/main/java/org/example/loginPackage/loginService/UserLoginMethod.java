//package org.example.services.login;
//
//import lombok.AllArgsConstructor;
//import org.example.dto.requests.loginRequest.LoginRequest;
//import org.example.dto.responses.loginResponse.LoginResponse;
//import org.example.securityConfig.JwtService;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class UserLoginMethod implements UserLoginImp {
//    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//
//    @Override
//    public LoginResponse login(LoginRequest loginRequest) {
//        if(loginRequest.getEmail() ==  null || loginRequest.getPassword() == null){
//            throw new IllegalArgumentException("You must provide a username and a password");
//        }
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginRequest.getEmail(),
//                loginRequest.getPassword())
//        );
//        String token = jwtService.GenerateToken(loginRequest.getEmail());
//        return LoginResponse.builder()
//                .token(token)
//                .message("Success")
//                .build();
//    }
//}
package org.example.loginPackage.loginService;

import lombok.AllArgsConstructor;
import org.example.data.repositories.UserRepository;
import org.example.loginPackage.dto.loginRequest.LoginRequest;
import org.example.loginPackage.dto.loginResponse.LoginResponse;
import org.example.securityConfig.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserLoginMethod implements UserLoginImp {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String loginId = loginRequest.getEmailOrStudentCode(); // May be email or studentCode
        String password = loginRequest.getPassword();

        if (loginId == null || password == null) {
            throw new IllegalArgumentException("You must provide a login ID and a password");
        }

        // Authenticate with Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginId, password)
        );

        // Try to find user by email, or fallback to studentCode
        var userOptional = userRepository.findByEmail(loginId)
                .or(() -> userRepository.findByStudentCode(loginId));

        var user = userOptional.orElseThrow(() ->
                new IllegalArgumentException("User not found with email or student code: " + loginId)
        );

        // Use the user's actual email to generate token
        String token = jwtService.GenerateToken(user.getEmail());

        return LoginResponse.builder()
                .token(token)
                .message("Success")
                .build();
    }

}
