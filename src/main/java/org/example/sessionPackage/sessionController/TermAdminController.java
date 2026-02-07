package org.example.sessionPackage.sessionController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sessionPackage.dto.sessionRequest.TermRequest;
import org.example.sessionPackage.dto.sessionResponse.TermDeletionResponse;
import org.example.sessionPackage.dto.sessionResponse.TermResponse;
import org.example.sessionPackage.sessionService.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/term")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class TermAdminController {

    private final TermCreateService createService;
    private final TermQueryService queryService;
    private final TermUpdateService updateService;
    private final TermDeleteService deleteService;
    private final TermRestoreService restoreService;
    private final TermActivateService activateService;
    private final TermCloseService closeService;


    @PostMapping
    public ResponseEntity<TermResponse> create(@RequestBody TermRequest request) {
        log.info("Using NEW create method (returns SessionResponse)");
        TermResponse response = createService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(queryService.findAll());
    }
    @PutMapping("/{sessionId}")
    public ResponseEntity<?> update(
            @PathVariable UUID sessionId,
            @RequestBody TermRequest request
    ) {
        TermResponse response = updateService.update(sessionId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<?> delete(@PathVariable UUID sessionId) {
        TermDeletionResponse response = deleteService.delete(sessionId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{sessionId}/restore")
    public ResponseEntity<?> restore(@PathVariable UUID sessionId) {
        restoreService.restore(sessionId);
        return ResponseEntity.ok("Session restored successfully");
    }

    @PutMapping("/{sessionId}/activate")
    public ResponseEntity<?> activate(@PathVariable UUID sessionId) {
        activateService.activate(sessionId);
        return ResponseEntity.ok("Session activated successfully");
    }

    @PutMapping("/{sessionId}/close")
    public ResponseEntity<?> close(@PathVariable UUID sessionId) {
        closeService.close(sessionId);
        return ResponseEntity.ok("Session successfully closed");
    }

}

