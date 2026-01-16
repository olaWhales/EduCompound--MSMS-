package org.example.parentPackage.parentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.Users;
import org.example.data.repositories.UserRepository;
import org.example.email.EmailEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ParentAdminServiceImpl implements ParentAdminService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void activateParent(UUID userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        user.setActive(true);
        userRepository.save(user);

        // 📧 SEND EMAIL HERE (BUSINESS EVENT)
        eventPublisher.publishEvent(
                new EmailEvent(
                        this,
                        user.getEmail(),
                        "Account Activated",
                        "Your parent account has been activated. You can now log in.",
                        false
                )
        );
    }

    @Override
    public void deactivateParent(UUID userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        user.setActive(false);
        userRepository.save(user);
    }


}
