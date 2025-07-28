package org.example.subjectPackage.subjectController;

import lombok.AllArgsConstructor;
import org.example.globalExceptionPackage.Valid;
import org.example.subjectPackage.dto.SubjectRequest.UpdateSubjectRequestDTO;
import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;
import org.example.subjectPackage.subjectService.UpdateSubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UpdateController {
    private final UpdateSubjectService updateSubjectService;

    @PutMapping("/update/subject/{id}")
    public ResponseEntity<SubjectResponseDTO> updateSubject(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateSubjectRequestDTO request) {

        SubjectResponseDTO response = updateSubjectService.updateSubject(id, request);
        return ResponseEntity.ok(response);
    }

}
