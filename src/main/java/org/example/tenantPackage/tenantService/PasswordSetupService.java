//package org.example.services.tenantService;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.example.data.model.Token;
//import org.example.data.model.Users;
//import org.example.data.repositories.TokenRepository;
//import org.example.data.repositories.UserRepository;
//import org.example.dto.requests.teacherRequest.PasswordSetupRequest;
//import org.example.email.EmailEvent;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//import java.util.UUID;
//
//import static org.example.utilities.Utilities.*;
//
//@Service
//@AllArgsConstructor
//@Slf4j
//public class PasswordSetupService {
//    private final UserRepository userRepository;
//    private final TokenRepository tokenRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final ApplicationEventPublisher eventPublisher;
//
//    @Transactional
//    public void initiatePasswordSetup(String email) {
//        Users user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MESSAGE + email));
//        if (user.getPassword() != null) {
//            throw new IllegalStateException(PASSWORD_ALREADY_SET_FOR_THE_USER_MESSAGE + email);
//        }
//
//        String token = UUID.randomUUID().toString();
//        Token tokenEntity = Token.builder()
//                .token(token)
//                .email(email)
//                .expiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24-hour expiry
//                .build();
//        tokenRepository.save(tokenEntity);
//
//        String setupLink = String.format("http://localhost:8080/api/tenant/setup-password?token=%s&email=%s", token, email);
//        String emailContent = String.format(
//                "<html><body><h2>Password Setup</h2><p>Click <a href='%s'>here</a> to set your password.</p></body></html>",
//                setupLink);
//        EmailEvent emailEvent = new EmailEvent(this, email, "Set Your Password", emailContent, true);
//        eventPublisher.publishEvent(emailEvent);
//        log.info("Password setup email sent to: {}", email);
//    }
//
//    @Transactional
//    public void setPassword(PasswordSetupRequest request, String token) {
//        Users user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MESSAGE + request.getEmail()));
//        if (user.getPassword() != null) {throw new IllegalStateException(PASSWORD_ALREADY_SET_FOR_THE_USER_MESSAGE + request.getEmail());}
//        Token tokenEntity = tokenRepository.findByToken(token).orElseThrow(() -> new IllegalArgumentException(INVALID_OR_EXPIRE_TOKEN));
//        if (tokenEntity.getExpiresAt().before(new Date())) {throw new IllegalArgumentException(TOKEN_HAS_EXPIRE_MESSAGE);}
//        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {throw new IllegalArgumentException(PASSWORD_IS_REQUIRE_MESSAGE);}
//
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        userRepository.save(user);
//        tokenRepository.delete(tokenEntity); // Invalidate token after use
//        log.info("Password set for user: {}", request.getEmail());
//    }
//}

package org.example.tenantPackage.tenantService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.data.model.Token;
import org.example.data.model.Users;
import org.example.data.repositories.TokenRepository;
import org.example.data.repositories.UserRepository;
import org.example.teacherPackage.dto.teacherRequest.PasswordSetupRequest;
import org.example.email.EmailEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

import static org.example.utilities.Utilities.*;

@Service
@AllArgsConstructor
@Slf4j
public class PasswordSetupService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void initiatePasswordSetup(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MESSAGE + email));
        if (user.getPassword() != null) {
            throw new IllegalStateException(PASSWORD_ALREADY_SET_FOR_THE_USER_MESSAGE + email);
        }

        String token = UUID.randomUUID().toString();
        Token tokenEntity = Token.builder()
                .token(token)
                .email(email)
                .expiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24-hour expiry
                .build();
        tokenRepository.save(tokenEntity);

        String setupLink = String.format(TENANT_SETUP_PASSWORD, token, email);
        String emailContent = String.format(CLICK_THE_LINK_TO_SETUP_THE_PASSWORD, setupLink);
        EmailEvent emailEvent = new EmailEvent(this, email, SET_YOUR_PASSWORD, emailContent, true);
        eventPublisher.publishEvent(emailEvent);
        log.info(PASSWORD_SETUP_EMAIL_SENT_TO, email);
    }

    @Transactional
    public void setPassword(PasswordSetupRequest request, String token) {
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND_MESSAGE + request.getEmail()));
        if (user.getPassword() != null) {
            throw new IllegalStateException(PASSWORD_ALREADY_SET_FOR_THE_USER_MESSAGE + request.getEmail());
        }
        Token tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_OR_EXPIRE_TOKEN));
        if (tokenEntity.getExpiresAt().before(new Date())) {
            throw new IllegalArgumentException(TOKEN_HAS_EXPIRE_MESSAGE);
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException(PASSWORD_IS_REQUIRE_MESSAGE);
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        tokenRepository.delete(tokenEntity); // Invalidate token after use
        log.info(PASSWORD_SETUP_EMAIL_SENT_TO, request.getEmail());
    }

    // 🔥 Add this new method below others
    @Transactional
    public void resendPasswordSetup(Users user) {
        // Remove any existing token for this email
        tokenRepository.findByEmail(user.getEmail()).ifPresent(tokenRepository::delete);

        String token = UUID.randomUUID().toString();
        Token tokenEntity = Token.builder()
                .token(token)
                .email(user.getEmail())
                .expiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24-hour expiry
                .build();
        tokenRepository.save(tokenEntity);

        String setupLink = String.format("http://localhost:8080/api/tenant/setup-password?token=%s&email=%s", token, user.getEmail());
        String emailContent = String.format(
                "<html><body><h2>Password Setup</h2><p>Click <a href='%s'>here</a> to set your password.</p></body></html>",
                setupLink);
        EmailEvent emailEvent = new EmailEvent(this, user.getEmail(), SET_YOUR_PASSWORD, emailContent, true);
        eventPublisher.publishEvent(emailEvent);
        log.info(RESENT_PASSWORD_SETUP_EMAIL_TO, user.getEmail());
    }
}
