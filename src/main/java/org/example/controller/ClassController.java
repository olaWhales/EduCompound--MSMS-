package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.requests.ClassRequest;
import org.example.dto.responses.ClassResponse;
import org.example.services.classService.ClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @PostMapping("/classes/create")
    public ResponseEntity<ClassResponse> createClass(@Valid @RequestBody ClassRequest request) {
        ClassResponse response = classService.classResponse(request);
        return ResponseEntity.ok(response);
    }
}
