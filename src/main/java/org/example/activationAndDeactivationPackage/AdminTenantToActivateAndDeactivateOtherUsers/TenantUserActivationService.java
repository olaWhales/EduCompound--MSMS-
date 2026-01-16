package org.example.activationAndDeactivationPackage.AdminTenantToActivateAndDeactivateOtherUsers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.Authentication.AuthenticatedUserProvider;
import org.example.data.model.Role;
import org.example.data.model.Users;
import org.example.data.repositories.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantUserActivationService {

    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authUserProvider;

    public void activateUser(String identifier) {
        Users admin = loadCurrentAdmin();
        Users user = loadTargetUser(identifier, admin);

        validateTenant(admin, user);
        validateRole(user);

        user.setActive(true);
        user.setStatusUpdatedAt(Instant.now());

        // optional but safe
        user.getAdminTenant().setActive(true);

        userRepository.save(user);
    }

    public void deactivateUser(String identifier) {
        Users admin = loadCurrentAdmin();
        Users user = loadTargetUser(identifier, admin);

        validateTenant(admin, user);
        validateRole(user);

        user.setActive(false);
        user.setStatusUpdatedAt(Instant.now());

        userRepository.save(user);
    }

    // =========================
    // 🔽 Helper methods
    // =========================

    private Users loadCurrentAdmin() {
        return userRepository
                .findByIdWithTenant(authUserProvider.getCurrentUser().getUserId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    private Users loadTargetUser(String identifier, Users admin) {

        // UUID → teacher / parent / admin
        if (isUUID(identifier)) {
            UUID userId = UUID.fromString(identifier);
            return userRepository
                    .findByIdWithTenant(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        // String → studentCode (stored as email)
        return userRepository
                .findByEmailAndAdminTenant(identifier, admin.getAdminTenant())
                .orElseThrow(() -> new RuntimeException("Student account not found"));
    }

    private void validateTenant(Users admin, Users user) {
        if (!user.getAdminTenant().equals(admin.getAdminTenant())) {
            throw new AccessDeniedException("Cannot manage user from another tenant");
        }
    }

    private void validateRole(Users user) {
        if (user.getRole() == Role.SUPER_ADMIN) {
            throw new IllegalStateException("Cannot modify super admin");
        }
    }

    private boolean isUUID(String value) {
        try {
            UUID.fromString(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
