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

import java.util.Date;

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

        log.info("Processing teacher registration for email: {}", request.getEmail());
        try {
            // Validate token
            Token tokenEntity = tokenRepository.findByTokenAndEmail(token, request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Invalid or expired token: " + token));
            if (tokenEntity.getExpiresAt().before(new Date())) {
                throw new RuntimeException("Token has expired");
            }
            if (tokenEntity.getAdminTenant() == null || tokenEntity.getAdminTenant().getTenantId() == null) {
                throw new IllegalStateException("AdminTenant not set or not persisted in token!");
            }
            // Check for existing user
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("Email already registered");
            }

// Re-fetch AdminTenant to ensure it's managed
            AdminTenant persistedTenant = adminTenantRepository.findById(
                    tokenEntity.getAdminTenant().getTenantId()
            ).orElseThrow(() -> new RuntimeException("AdminTenant not found in DB"));

// Now set the tenant on the new user
            Users user = new Users();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(org.example.data.model.Role.TEACHER);
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPhone(request.getPhoneNumber());
            user.setAdminTenant(persistedTenant); // ✅ This is now safe
            user.setCreatedAt(new Date());

            log.info("AdminTenant in token: {}", tokenEntity.getAdminTenant());
            log.info("Tenant ID: {}", tokenEntity.getAdminTenant() != null ? tokenEntity.getAdminTenant().getTenantId() : "null");

            user = userRepository.save(user);

            // Create teacher
            Teacher teacher = new Teacher();
            teacher.setUsers(user);
//            teacher.setFirstName(request.getFirstName());
//            teacher.setLastName(request.getLastName());
            teacher.setAdminTenant(tokenEntity.getAdminTenant()); // Link to inviting admin
//            teacher.setTenantId(tokenEntity.getAdmin().getTenantId()); // Assuming tenant_id from admin
            teacherRepository.save(teacher);

            // Invalidate token
            tokenRepository.delete(tokenEntity);
            log.info("Teacher registered successfully: {}", request.getEmail());
        } catch (Exception e) {
            log.error("Error during teacher registration: {}", e.getMessage(), e);
            throw e; // Re-throw to trigger 500 response with details
        }
    }
}