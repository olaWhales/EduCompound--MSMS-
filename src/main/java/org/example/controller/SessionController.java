package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.requests.SessionRequest;
import org.example.dto.responses.SessionResponse;
import org.example.services.sessionService.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/session/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SessionResponse> createSession(@Valid @RequestBody SessionRequest request) {
        return ResponseEntity.ok(sessionService.sessionResponse(request));
    }
}
