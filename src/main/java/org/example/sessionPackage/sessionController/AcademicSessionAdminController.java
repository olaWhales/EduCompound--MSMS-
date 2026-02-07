package org.example.sessionPackage.sessionController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sessionPackage.dto.sessionRequest.AcademicSessionCreateRequest;
import org.example.sessionPackage.dto.sessionResponse.AcademicSessionResponse;
import org.example.sessionPackage.sessionService.AcademicSessionCreateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/academic-sessions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class AcademicSessionAdminController {

    private final AcademicSessionCreateService createService;

    @PostMapping
    public ResponseEntity<AcademicSessionResponse> create(
            @RequestBody AcademicSessionCreateRequest request
    ) {
        log.info("Creating academic session: {}", request.getSessionYear());
        AcademicSessionResponse response = createService.create(request);
        return ResponseEntity.ok(response);
    }
}
