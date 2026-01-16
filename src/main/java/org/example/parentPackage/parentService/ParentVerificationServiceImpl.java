package org.example.parentPackage.parentService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.Users;
import org.example.data.repositories.UserRepository;
import org.example.email.EmailEvent;
import org.example.tokenPackage.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;


@Service
@RequiredArgsConstructor
public class ParentVerificationServiceImpl implements ParentVerificationService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    public void verifyToken(String email, String token, String password) {
        if (!tokenService.validateToken(email, token)) {throw new IllegalArgumentException("Invalid or expired token.");}
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setPassword(passwordEncoder.encode(password));
        user.setVerified(true);
        user.setActive(false);  // comment says activate, but false
        userRepository.save(user);
        tokenService.markEmailAsVerified(email);
    }

    @Override
    public void resendVerificationToken(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found."));
        String newToken = tokenService.generateSixDigitToken();
        // Assume Token entity and repository are set up
        tokenService.saveToken(email, newToken, user.getAdminTenant());
        String body = "Dear " + user.getFirstName() + ",\n\nYour new verification code is: **" + newToken + "**\n\nThis code will expire in 15 minutes.";
//         Assuming EmailEvent is published here; adjust based on your implementation
         eventPublisher.publishEvent(new EmailEvent(this, email, "Resend Verification Code", body, false));
    }
}