package org.example.teacherPackage.teacherService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.data.model.AdminTenant;
import org.example.data.model.Teacher;
import org.example.data.model.Token;
import org.example.data.model.Users;
import org.example.data.repositories.AdminTenantRepository;
import org.example.data.repositories.TeacherRepository;
import org.example.data.repositories.TokenRepository;
import org.example.data.repositories.UserRepository;
import org.example.teacherPackage.dto.teacherRequest.TeacherRegistrationRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

import static org.example.utilities.Utilities.*;

@Service
@AllArgsConstructor
@Slf4j
public class TeacherRegistrationServiceImpl implements TeacherRegistrationService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminTenantRepository adminTenantRepository ;

    @Transactional
    @Override
    public void completeTeacherRegistration(TeacherRegistrationRequest request, String token) {

        log.info(PROCESSING_TEACHER_REGISTRATION_FOR_EMAIL, request.getEmail());
        try {
            // Validate token
            Token tokenEntity = tokenRepository.findByTokenAndEmail(token, request.getEmail()).orElseThrow(() -> new RuntimeException(INVALID_OR_EXPIRE_TOKEN + token));
            if (tokenEntity.getExpiresAt().before(new Date())) {throw new RuntimeException(TOKEN_HAS_EXPIRE_MESSAGE);}
            if (tokenEntity.getAdminTenant() == null || tokenEntity.getAdminTenant().getTenantId() == null) {throw new IllegalStateException(ADMINTENANT_NOT_SET_OR_NOT_PERSISTED_IN_TOKEN);}
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {throw new RuntimeException(EMAIL_ALREADY_REGISTERED);}

            // Re-fetch AdminTenant to ensure it's managed
            AdminTenant persistedTenant = adminTenantRepository.findById(
                    tokenEntity.getAdminTenant().getTenantId()
            ).orElseThrow(() -> new RuntimeException(ADMINTENANT_NOT_FOUND_IN_DB));

            // Now set the tenant on the new user
            Users user = new Users();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(org.example.data.model.Role.TEACHER);
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPhone(request.getPhoneNumber());
            user.setAdminTenant(persistedTenant);
            user.setCreatedAt(new Date());
            user.setVerified(true);   // ✅ teachers do NOT verify email
            user.setActive(false);   // ✅ admin must activate
            user.setStatusUpdatedAt(Instant.now());


            log.info("AdminTenant in token: {}", tokenEntity.getAdminTenant());
            log.info("Tenant ID: {}", tokenEntity.getAdminTenant() != null ? tokenEntity.getAdminTenant().getTenantId() : "null");
            user = userRepository.save(user);

            // Create teacher
            Teacher teacher = new Teacher();
            teacher.setUsers(user);
            teacher.setAdminTenant(tokenEntity.getAdminTenant()); // Link to inviting admin
            teacherRepository.save(teacher);

            // Invalidate token
            tokenRepository.delete(tokenEntity);
            log.info(TEACHER_REGISTERED_SUCCESSFULLY , request.getEmail());
        } catch (Exception e) {
            log.error(ERROR_DURING_TEACHER_REGISTRATION, e.getMessage(), e);
            throw e; // Re-throw to trigger 500 response with details
        }
    }
}