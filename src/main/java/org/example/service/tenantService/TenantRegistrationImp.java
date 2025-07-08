package org.example.service.tenantService;

import lombok.AllArgsConstructor;
import org.example.data.model.Role;
import org.example.data.model.Tenant;
import org.example.data.model.UserPrincipal;
import org.example.data.model.Users;
import org.example.data.repositories.TenantRepository;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.TenantCreationRequest;
import org.example.dto.responses.TenantCreationResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.example.utilities.Utilities.REGISTRATION_SUCCESS;

@Service
@AllArgsConstructor
public class TenantRegistrationImp implements TenantRegistration {

    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public TenantCreationResponse createTenant(TenantCreationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){throw new IllegalArgumentException("Authentication required");}
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Users users = userPrincipal.users();
        userRepository.findByEmail(users.getEmail());

        Tenant tenant = Tenant.builder()
                .schoolName(request.getSchoolName())
                .subdomain(request.getSubdomain())
                .createdAt(new Date())
                .build();
        Tenant tenants = tenantRepository.save(tenant);
        Users user = Users.builder()
                .tenant(tenant)
                .email(request.getAdminEmail())
                .password(passwordEncoder.encode(request.getAdminPassword()))
                .firstName(request.getAdminFirstName())
                .lastName(request.getAdminLastName())
                .role(Role.ADMIN)
                .createdAt(new Date())
                .build();
        userRepository.save(user);
        return TenantCreationResponse.builder()
                .schoolName(tenants.getSchoolName())
                .subDomain(tenants.getSubdomain())
                .message(REGISTRATION_SUCCESS)
                .dateCreated(new Date())
                .build();
    }
}
