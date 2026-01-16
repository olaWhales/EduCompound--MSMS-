package org.example.acountStatusPolicy;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.Authentication.AuthenticatedUserProvider;
import org.example.data.model.Role;
import org.example.data.model.Users;
import org.example.data.repositories.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountActivationServiceImpl implements AccountActivationService {

    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;


    @Override
    public void changeStatus(UUID targetUserId, boolean active) {

        Users actor = authenticatedUserProvider.getCurrentUser();
        Users target = userRepository.findById(targetUserId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        validatePermission(actor, target);

        target.setActive(active);
        userRepository.save(target);
    }

    private void validatePermission(Users actor, Users target) {
        // SUPER_ADMIN → ADMIN_TENANT
        if (actor.getRole() == Role.SUPER_ADMIN && target.getRole() == Role.ADMIN) {
            return;
        }

        // ADMIN_TENANT → non-admin users
        if (actor.getRole() == Role.ADMIN && target.getRole() != Role.ADMIN && target.getRole() != Role.SUPER_ADMIN) {
            return;
        }
        throw new AccessDeniedException("You are not allowed to change this user's status");
    }
}
