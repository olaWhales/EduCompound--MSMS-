package org.example.loginPackage.loginService;

import org.example.data.model.Role;
import org.example.data.model.Users;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Component;

@Component
public class AccountStatusPolicy {

    public void validateLogin(Users user) {

        // 1️⃣ Parents must verify email first
        if (user.getRole() == Role.PARENT && !user.isVerified()) {throw new DisabledException("Email not verified.");}
        // 2️⃣ Everyone except SUPER_ADMIN must be activated
        if (user.getRole() != Role.SUPER_ADMIN && !user.isActive()) {throw new DisabledException("Account awaiting activation.");}
        // 3️⃣ Tenant activation (except SUPER_ADMIN)
        if (user.getRole() != Role.SUPER_ADMIN) {
            if (user.getAdminTenant() == null || !user.getAdminTenant().isActive()) {throw new DisabledException("School is not active.");}}
        // 3️⃣ Password must exist (important!)
        if (user.getPassword() == null) {throw new DisabledException("Password not set.");}
    }
}
