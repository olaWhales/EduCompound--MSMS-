package org.example.loginPackage.loginService;

import org.example.data.model.Role;

public record LoginContext(
        Role role,
        boolean userActive,
        boolean emailVerified,
        boolean tenantActive
) {}
