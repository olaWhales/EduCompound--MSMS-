package org.example.sessionPackage.sessionController;

import lombok.RequiredArgsConstructor;
import org.example.sessionPackage.dto.sessionRequest.SessionRequest;
import org.example.sessionPackage.sessionService.SessionCreateService;
import org.example.sessionPackage.sessionService.SessionDeleteService;
import org.example.sessionPackage.sessionService.SessionQueryService;
import org.example.sessionPackage.sessionService.SessionUpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/sessions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SessionAdminController {

    private final SessionCreateService createService;
    private final SessionQueryService queryService;
    private final SessionUpdateService updateService;
    private final SessionDeleteService deleteService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SessionRequest request) {
        return ResponseEntity.ok(createService.create(request));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(queryService.findAll());
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<?> update(
            @PathVariable UUID sessionId,
            @RequestBody SessionRequest request
    ) {
        updateService.update(sessionId, request);
        return ResponseEntity.ok("Session updated");
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<?> delete(@PathVariable UUID sessionId) {
        deleteService.delete(sessionId);
        return ResponseEntity.ok("Session deleted");
    }
}

