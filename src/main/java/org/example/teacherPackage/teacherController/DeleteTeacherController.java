package org.example.teacherPackage.teacherController;

import lombok.AllArgsConstructor;
import org.example.teacherPackage.teacherService.TeacherDeleteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
@AllArgsConstructor
public class DeleteTeacherController {
    private final TeacherDeleteService teacherDeleteService;


    @DeleteMapping("/delete/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTeacher(@PathVariable("email") String email) {
        try {
            return new ResponseEntity<>(teacherDeleteService.deleteTeacher(email), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
