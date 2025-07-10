package org.example.service;

import lombok.AllArgsConstructor;
import org.example.data.model.*;
import org.example.data.repositories.AdminTenantRepository;
import org.example.data.repositories.TokenRepository;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.AdminInitiateTeacher;
import org.example.email.EmailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class TeacherInvitationServiceImpl implements TeacherInvitationService {
    private static final Logger log = LoggerFactory.getLogger(TeacherInvitationServiceImpl.class);
    private final TokenRepository tokenRepository;
    private final UserRepository usersRepository;
    private final AdminTenantRepository adminTenantRepository;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;
    private final AppProperties appProperties;

    @Override
    public void initiateTeacherRegistration(AdminInitiateTeacher request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserPrincipal userPrincipal)) {
            throw new IllegalStateException("Authentication required with UserPrincipal");
        }
        Users user = userPrincipal.users();
        if (user.getRole() != Role.ADMIN) {
            throw new IllegalStateException("User is not an admin");
        }
        
        
        AdminTenant adminTenant = adminTenantRepository.findById(user.getAdminTenant().getTenantId())
                .orElseThrow(() -> new RuntimeException("AdminTenant not found for user"));

//        AdminTenant adminTenant = user.getAdminTenant();
//        if (adminTenant == null) {
//            throw new RuntimeException("Admin entity not found for user");
//        }

        String token = tokenService.generateSixDigitToken();
        Token tokenEntity = Token.builder()
                .token(token)
                .email(request.getEmail())
                .adminTenant(adminTenant)
                .createdAt(new Date())
                .expiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .build();
        tokenRepository.save(tokenEntity);

        // Log the base URL
        log.info("Base URL: {}", appProperties.getBaseUrl());

        String baseUrl = appProperties.getBaseUrl() != null ? appProperties.getBaseUrl() : "http://localhost:8080";
        String registrationLink = String.format("%s/api/teacher/register/teacher?token=%s&email=%s",
                baseUrl, token, request.getEmail());

        // Log the generated link
        log.info("Registration link: {}", registrationLink);

        String emailContent = String.format(
                "<html>" +
                        "<body>" +
                        "<h2>Teacher Registration Invitation</h2>" +
                        "<p>You have been invited to join as a teacher at %s.</p>" +
                        "<p><strong>Token:</strong> %s</p>" +
                        "<p><strong>Registration Link:</strong> <a href='%s'>Click here to register</a></p>" +
                        "<p>This link will expire in 24 hours.</p>" +
                        "<p>If you did not request this, please ignore this email.</p>" +
                        "</body>" +
                        "</html>",
                adminTenant.getSchoolName(), token, registrationLink);

        EmailEvent emailEvent = new EmailEvent(
                this,
                request.getEmail(),
                "Teacher Registration Invitation",
                emailContent,
                true
        );
        eventPublisher.publishEvent(emailEvent);
    }
}