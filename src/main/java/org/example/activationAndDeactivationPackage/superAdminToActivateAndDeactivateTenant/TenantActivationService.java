package org.example.activationAndDeactivationPackage.superAdminToActivateAndDeactivateTenant;

import java.util.UUID;

public interface TenantActivationService {

    void activateTenant(UUID tenantId);

    void deactivateTenant(UUID tenantId);
}
