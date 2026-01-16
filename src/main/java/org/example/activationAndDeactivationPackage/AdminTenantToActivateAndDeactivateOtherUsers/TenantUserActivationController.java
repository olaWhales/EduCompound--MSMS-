package org.example.activationAndDeactivationPackage.AdminTenantToActivateAndDeactivateOtherUsers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class TenantUserActivationController {

    private final TenantUserActivationService activationService;

//    @PatchMapping("/{userId}/activate")
//    public ResponseEntity<?> activateUser(@PathVariable UUID userId) {
//        activationService.activateUser(userId);
//        return ResponseEntity.ok("User activated");
//    }
//
//    @PatchMapping("/{userId}/deactivate")
//    public ResponseEntity<?> deactivateUser(@PathVariable UUID userId) {
//        activationService.deactivateUser(userId);
//        return ResponseEntity.ok("User deactivated");
//    }
    @PatchMapping("/{identifier}/activate")
    public ResponseEntity<?> activateUser(@PathVariable String identifier) {
        activationService.activateUser(identifier);
        return ResponseEntity.ok("User activated successfully");
    }

        @PatchMapping("/{identifier}/deactivate")
        public ResponseEntity<?> deactivateUser(@PathVariable String identifier) {
            activationService.deactivateUser(identifier);
            return ResponseEntity.ok("User deactivated successfully");
        }

}
