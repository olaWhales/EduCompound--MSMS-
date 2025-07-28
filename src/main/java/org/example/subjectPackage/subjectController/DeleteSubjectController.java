package org.example.subjectPackage.subjectController;

import lombok.RequiredArgsConstructor;
import org.example.subjectPackage.subjectService.DeleteSubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DeleteSubjectController {
    private final DeleteSubjectService deleteSubjectService ;

    @DeleteMapping("/delete_subject/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable UUID id) {
        deleteSubjectService.deleteSubjectById(id);
        return ResponseEntity.ok("Subject deleted successfully.");
    }

}
