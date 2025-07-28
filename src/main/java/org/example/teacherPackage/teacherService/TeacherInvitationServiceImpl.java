package org.example.teacherPackage.teacherService;

import lombok.AllArgsConstructor;
import org.example.data.model.*;
import org.example.data.repositories.AdminTenantRepository;
import org.example.data.repositories.TokenRepository;
import org.example.data.repositories.UserRepository;
import org.example.teacherPackage.dto.teacherRequest.AdminInitiateTeacher;
import org.example.email.EmailEvent;
import org.example.utilities.AppProperties;
import org.example.tokenPackage.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.example.utilities.Utilities.*;

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
        if (!(principal instanceof UserPrincipal userPrincipal)) {throw new IllegalStateException(AUTHENTICATION_REQUIRE_MESSAGE);}
        Users user = userPrincipal.users();
        if (user.getRole() != Role.ADMIN) {throw new IllegalStateException(USER_IS_NOT_AN_ADMIN_MESSAGE);}
        AdminTenant adminTenant = adminTenantRepository.findById(user.getAdminTenant().getTenantId()).orElseThrow(() -> new RuntimeException(ADMIN_TENANT_NOT_FOUND));
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
//        log.info("Base URL: {}", appProperties.getBaseUrl());
        String baseUrl = appProperties.getBaseUrl() != null ? appProperties.getBaseUrl() : "http://localhost:8080";
        String registrationLink = String.format("%s/api/teacher/register/teacher?token=%s&email=%s",
                baseUrl, token, request.getEmail());

        // Log the generated link
//        log.info("Registration link: {}", registrationLink);
        String emailContent = String.format(REGISTRATION_INVITATION_MESSAGE, adminTenant.getSchoolName(), token, registrationLink);
        EmailEvent emailEvent = new EmailEvent(
                this,
                request.getEmail(),
                TEACHER_REGISTRATION_SUCCESSFUL_MESSAGE,
                emailContent,
                true
        );
        eventPublisher.publishEvent(emailEvent);
    }
}