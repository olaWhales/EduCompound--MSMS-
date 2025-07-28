package org.example.assignTeacherSubjectClassPackage.teacherSubjectController;

import lombok.RequiredArgsConstructor;
import org.example.assignTeacherSubjectClassPackage.dto.assignTeacherSubjectRequest.AssignTeacherSubjectClassRequest;
import org.example.assignTeacherSubjectClassPackage.dto.assignTeacherSubjectResponse.AssignTeacherSubjectClassResponse;
import org.example.assignTeacherSubjectClassPackage.assignTeacherSubjectService.AssignTeacherSubjectClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeacherSubjectClassController {

    private final AssignTeacherSubjectClassService assignService;

    @PostMapping("/teacher-subject-class/assign")
    public ResponseEntity<AssignTeacherSubjectClassResponse> assign(@RequestBody AssignTeacherSubjectClassRequest request) {
        return ResponseEntity.ok(assignService.assign(request));
    }
}
