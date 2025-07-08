package org.example.service;

import lombok.AllArgsConstructor;
import org.example.data.model.Admin;
import org.example.data.model.Role;
import org.example.data.model.Users;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.AdminInitiateTeacher;
import org.example.email.EmailEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class TeacherInvitationServiceImp implements TeacherInvitationService {
    private final UserRepository usersRepository;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;

    ArrayList<String>save;

    @Override
    public void initiateTeacherRegistration(AdminInitiateTeacher request, Long tenantId) {
        // Create a temporary user entry with a token
        String token = tokenService.generateSixDigitToken();
        Users user = Users.builder()
                .email(request.getEmail())
                .role(Role.TEACHER)
                .admin(Admin.builder().tenantId(tenantId).build())
                .build();
        String temporarySave =
        usersRepository.save(user);

        // Create registration link
        String registrationLink = String.format("http://%s/register/teacher?token=%s&email=%s",
                "your-domain.com", token, request.getEmail());

        // Send email
        String emailContent = String.format(
                "You have been invited to join as a teacher. Please complete your registration using this code: %s\nLink: %s",
                token, registrationLink);
        EmailEvent emailEvent = new EmailEvent(
                this,
                request.getEmail(),
                "Teacher Registration Invitation",
                emailContent
        );
        eventPublisher.publishEvent(emailEvent);
    }
}