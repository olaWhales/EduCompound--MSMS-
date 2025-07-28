package org.example.subjectPackage.subjectController;

import lombok.RequiredArgsConstructor;
import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;
import org.example.subjectPackage.subjectService.ViewSubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ViewSubjectController {

    private final ViewSubjectService viewSubjectService;

    @GetMapping("/all/subjects")
    public ResponseEntity<List<SubjectResponseDTO>> getAllSubjects() {
        return ResponseEntity.ok(viewSubjectService.getAllSubjects());
    }
}
