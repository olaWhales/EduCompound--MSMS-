package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.requests.ParentRegisterRequest;
import org.example.dto.responses.ParentRegisterResponse;
import org.example.services.parentService.ParentRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentRegisterService parentRegisterService;

    @PostMapping("/register")
    public ResponseEntity<ParentRegisterResponse> registerParent(@RequestBody ParentRegisterRequest request) {
        ParentRegisterResponse response = parentRegisterService.registerParent(request);
        return ResponseEntity.ok(response);
    }
}
