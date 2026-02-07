package org.example.sessionPackage.sessionService;

import org.example.data.model.AdminTenant;
import org.example.data.model.Users;

import java.util.UUID;

public interface TenantContextResolver {
    UUID currentTenantId();
}
