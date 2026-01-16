package org.example.activationAndDeactivationPackage.superAdminToActivateAndDeactivateTenant;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.model.Role;
import org.example.data.repositories.AdminTenantRepository;
import org.example.data.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantActivationServiceImpl implements TenantActivationService {

    private final AdminTenantRepository adminTenantRepository;
    private final UserRepository userRepository;

    @Override
    public void activateTenant(UUID tenantId) {

        AdminTenant tenant = adminTenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        tenant.setActive(true);

        // 🔥 Activate tenant admin(s)
        userRepository.findAllByAdminTenantAndRole(tenant, Role.ADMIN)
                .forEach(admin -> admin.setActive(true));
    }

    @Override
    public void deactivateTenant(UUID tenantId) {

        AdminTenant tenant = adminTenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        tenant.setActive(false);

        // Optional but recommended
        userRepository.findAllByAdminTenant(tenant)
                .forEach(user -> user.setActive(false));
    }
}
