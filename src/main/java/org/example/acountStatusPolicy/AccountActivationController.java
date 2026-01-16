package org.example.acountStatusPolicy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountActivationController {

    private final AccountActivationService activationService;

    @PatchMapping("/{userId}/status")
    public ResponseEntity<?> changeStatus(
            @PathVariable UUID userId,
            @RequestParam boolean active) {

        activationService.changeStatus(userId, active);
        return ResponseEntity.ok("Account status updated");
    }
}
