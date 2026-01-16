package org.example.parentPackage.parentController;

import lombok.RequiredArgsConstructor;
import org.example.parentPackage.parentService.ParentAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/parents")
public class ParentAdminController {

    private final ParentAdminService parentAdminService;

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<?> activate(@PathVariable UUID userId) {
        parentAdminService.activateParent(userId);
        return ResponseEntity.ok("Parent account activated");
    }

    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable UUID userId) {
        parentAdminService.deactivateParent(userId);
        return ResponseEntity.ok("Parent account deactivated");
    }
}
