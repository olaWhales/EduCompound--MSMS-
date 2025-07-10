package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.model.Role;
import org.example.data.model.Users;
import org.example.data.repositories.AdminTenantRepository;
import org.example.data.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {
    private final UserRepository userRepository;
    private final AdminTenantRepository adminTenantRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedSuperAdmin() {
        return args -> {
            String email = "superadmin@educare.com";
            AdminTenant defaultTenant = adminTenantRepository.findAll().stream().findFirst().orElseGet(() -> {
                AdminTenant tenant = new AdminTenant();
                tenant.setSchoolName("Global Tenant");
                tenant.setSubdomain("global");
                tenant.setCreatedAt(new Date());
                return adminTenantRepository.save(tenant);
            });

            if (userRepository.findByEmail(email).isEmpty()) {
                Users superAdmin = Users.builder()
                        .email(email)
                        .password(passwordEncoder.encode("supersecurepassword"))
                        .role(Role.SUPER_ADMIN)
                        .adminTenant(defaultTenant) // Associate with default tenant
                        .createdAt(new Date())
                        .build();
                userRepository.save(superAdmin);
                System.out.println("✅ Super Admin created: " + email);
            } else {
                System.out.println("ℹ️ Super Admin already exists: " + email);
            }
        };
    }
}