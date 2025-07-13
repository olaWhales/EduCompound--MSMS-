package org.example.service.tenantService;

import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.data.model.Role;
import org.example.data.model.AdminTenant;
import org.example.data.model.UserPrincipal;
import org.example.data.model.Users;
import org.example.data.repositories.AdminTenantRepository;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.TenantCreationRequest;
import org.example.dto.responses.TenantCreationResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;

import static org.example.utilities.Utilities.*;

@Service
@AllArgsConstructor
@Slf4j
public class TenantRegistrationImp implements TenantRegistration {
    private final AdminTenantRepository adminTenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PasswordSetupService passwordSetupService;

//    @PersistenceContext
//    private EntityManager entityManager;// Inject EntityManager for manual flush
    @Transactional
    @Override
    public TenantCreationResponse createTenant(TenantCreationRequest request) {
        // Step 1: Get the current user (just for validation, do not save it)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {throw new IllegalArgumentException(AUTHENTICATION_REQUIRE_MESSAGE);}
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Users currentUser = userPrincipal.users();
        if (userRepository.findByEmail(currentUser.getEmail()).isEmpty()) {throw new IllegalArgumentException(AUTHENTICATION_NOT_FOUND_MESSAGE);}
        // Step 2: Validate admin email is not taken
        if (userRepository.findByEmail(request.getAdminEmail()).isPresent()) {throw new IllegalArgumentException(ADMIN_ALREADY_REGISTERED_MESSAGE);}
        // Step 3: Save the tenant
        AdminTenant adminTenant = AdminTenant.builder()
                .schoolName(request.getSchoolName())
                .subdomain(request.getSubdomain())
                .createdAt(new Date())
                .build();
        AdminTenant savedTenant = adminTenantRepository.saveAndFlush(adminTenant);
        // Step 4: Save the admin user without password
        Users newAdmin = Users.builder()
                .adminTenant(savedTenant)
                .email(request.getAdminEmail())
                .firstName(request.getAdminFirstName())
                .lastName(request.getAdminLastName())
                .role(Role.ADMIN)
                .createdAt(new Date())
                .build();
        userRepository.save(newAdmin);
        // Step 5: Initiate password setup via email
        passwordSetupService.initiatePasswordSetup(request.getAdminEmail());
        // Step 6: Build response
        return TenantCreationResponse.builder()
                .schoolName(savedTenant.getSchoolName())
                .subDomain(savedTenant.getSubdomain())
                .message(REGISTRATION_SUCCESS)
                .dateCreated(new Date())
                .build();
    }
}