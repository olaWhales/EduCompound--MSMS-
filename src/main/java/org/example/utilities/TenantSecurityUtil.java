package org.example.utilities;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.model.UserPrincipal;
import org.example.data.model.Users;
import org.example.data.repositories.AdminTenantRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class TenantSecurityUtil {

    private final AdminTenantRepository adminTenantRepository;

    public AdminTenant getAuthenticatedTenant() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found.");
        }

        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        Users adminUser = principal.users();

        AdminTenant tenant = adminUser.getAdminTenant();
        if (tenant == null) {
            throw new IllegalStateException("Admin user has no associated tenant.");
        }

        return tenant;
    }
}