package org.example.subjectPackage.subjectController;

import lombok.RequiredArgsConstructor;
import org.example.subjectPackage.dto.SubjectRequest.SubjectRequestDTO;
import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;
import org.example.subjectPackage.subjectService.CreateSubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SubjectCreationController {

    private final CreateSubjectService createSubjectService;

    @PostMapping("/subjects/creation")
    public ResponseEntity<SubjectResponseDTO> createSubject(@Valid @RequestBody SubjectRequestDTO request) {
        SubjectResponseDTO response = createSubjectService.createSubject(request);
    return ResponseEntity.ok(response);
    }
}
