package org.example.loginPackage.loginService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.Users;
import org.example.data.repositories.UserRepository;
import org.example.loginPackage.dto.loginRequest.LoginRequest;
import org.example.loginPackage.dto.loginResponse.LoginResponse;
import org.example.securityConfig.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountStatusPolicy accountStatusPolicy;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {

        Users user = userRepository
                .findByEmailIgnoreCaseOrStudentCode(
                        request.getEmailOrStudentCode(),
                        request.getEmailOrStudentCode()
                )
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

        // ✅ ALL access rules here
        accountStatusPolicy.validateLogin(user);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return buildLoginResponse(user);
    }


    private LoginResponse buildLoginResponse(Users user) {
        return LoginResponse.builder()
                .token(jwtService.generateToken(user.getEmail()))
                .message("Login successful")
                .build();
    }
}
