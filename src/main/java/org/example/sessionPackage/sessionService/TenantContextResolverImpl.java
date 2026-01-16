package org.example.sessionPackage.sessionService;

import org.example.data.model.AdminTenant;
import org.example.data.model.UserPrincipal;
import org.example.data.model.Users;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TenantContextResolverImpl implements TenantContextResolver {

    @Override
    public Users currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        return principal.users();
    }

    @Override
    public AdminTenant currentTenant() {
        return currentUser().getAdminTenant();
    }
}
