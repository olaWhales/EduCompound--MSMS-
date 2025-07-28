package org.example.teacherPackage.teacherController;

import lombok.AllArgsConstructor;
import org.example.teacherPackage.dto.teacherRequest.TeacherUpdateRequest;
import org.example.teacherPackage.teacherService.TeacherUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
@AllArgsConstructor
public class UpdateTeacherController {
    private final TeacherUpdateService teacherUpdateService;

    @PutMapping("/update/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTeacher(@PathVariable("email") String email, @RequestBody TeacherUpdateRequest request) {
        try {
            return new ResponseEntity<>(teacherUpdateService.updateTeacher(email, request), HttpStatus.OK);
        } catch (Exception exception) {
            System.out.println("Current Auth: " + SecurityContextHolder.getContext().getAuthentication());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
