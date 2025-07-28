// SubjectController.java
package org.example.subjectPackage.subjectController;

import lombok.RequiredArgsConstructor;
import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;
import org.example.subjectPackage.subjectService.ViewSingleSubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ViewSingleSubjectController {

    private final ViewSingleSubjectService viewSubjectService;

    @GetMapping("/view_single/subject/{subjectId}")
    public ResponseEntity<SubjectResponseDTO> getSubjectById(@PathVariable UUID subjectId) {
        return ResponseEntity.ok(viewSubjectService.getSubjectById(subjectId));
    }
}
