package org.example.sessionPackage.sessionController;

import lombok.RequiredArgsConstructor;
import org.example.globalExceptionPackage.Valid;
import org.example.sessionPackage.dto.sessionRequest.SessionRequest;
import org.example.sessionPackage.dto.sessionResponse.SessionResponse;
import org.example.sessionPackage.sessionService.SessionService;
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
