package org.example.tokenPackage;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.Token;
import org.example.data.model.Users;
import org.example.data.repositories.TokenRepository;
import org.example.data.repositories.UserRepository;
import org.example.tokenPackage.dto.tokenRequest.OtpRequest;
import org.example.tokenPackage.dto.tokenResponse.OtpResponse;
import org.example.email.EmailEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpVerificationService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    private static final int EXPIRY_MINUTES = 15;

    @Transactional
    public OtpResponse resendOtpIfExpired(OtpRequest request) {
        Optional<Users> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return new OtpResponse(false, "No user found with this email. Please register first.");
        }

        Users user = userOpt.get();

        // Delete existing tokens (cleanup)
        tokenRepository.deleteByEmail(request.getEmail());

        // Generate new token
        String newToken = String.format("%06d", (int)(Math.random() * 1000000));
        Token token = Token.builder()
                .token(newToken)
                .email(request.getEmail())
                .adminTenant(user.getAdminTenant())
                .createdAt(new Date())
                .expiresAt(new Date(System.currentTimeMillis() + EXPIRY_MINUTES * 60 * 1000))
                .build();
        tokenRepository.save(token);

        // Send email
        String subject = "Your OTP Code (Resent)";
        String body = "Dear " + user.getFirstName() + ",\n\nYour new verification code is: **" + newToken + "**.\n\nThis code will expire in 15 minutes.";
        eventPublisher.publishEvent(new EmailEvent(this, request.getEmail(), subject, body, false));

        return new OtpResponse(true, "A new verification code has been sent to your email.");
    }
}
