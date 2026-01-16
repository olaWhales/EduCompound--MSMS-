package org.example.activationAndDeactivationPackage.superAdminToActivateAndDeactivateTenant;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/super-admin/tenants")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminTenantController {

    private final TenantActivationService tenantActivationService;

    @PatchMapping("/{tenantId}/activate")
    public ResponseEntity<?> activate(@PathVariable UUID tenantId) {
        tenantActivationService.activateTenant(tenantId);
        return ResponseEntity.ok("Tenant activated");
    }

    @PatchMapping("/{tenantId}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable UUID tenantId) {
        tenantActivationService.deactivateTenant(tenantId);
        return ResponseEntity.ok("Tenant deactivated");
    }
}
