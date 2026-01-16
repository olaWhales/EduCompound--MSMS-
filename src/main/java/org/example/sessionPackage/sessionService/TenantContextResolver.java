package org.example.sessionPackage.sessionService;

import org.example.data.model.AdminTenant;
import org.example.data.model.Users;

public interface TenantContextResolver {
    Users currentUser();
    AdminTenant currentTenant();
}
