package org.example.service;

import lombok.AllArgsConstructor;
import org.example.data.model.Tenant;
import org.example.data.model.Users;
import org.example.data.repositories.TenantRepository;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.TenantCreationRequest;
import org.example.dto.responses.TenantCreationResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.example.utilities.Utilities.REGISTRATION_SUCCESS;

@Service
@AllArgsConstructor
public class TenantServiceImp implements TenantService {

    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public TenantCreationResponse createTenant(TenantCreationRequest request) {

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
                .createdAt(new Date())
                .build();
        userRepository.save(user);

        TenantCreationResponse.builder()
                .schoolName(tenants.getSchoolName())
                .subDomain(tenants.getSubdomain())
                .message(REGISTRATION_SUCCESS)
                .dateCreated(new Date())
                .build();

        return null;
    }
}
