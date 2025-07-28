package org.example.teacherPackage.teacherController;

import lombok.AllArgsConstructor;
import org.example.teacherPackage.dto.teacherResponse.TeacherListResponse;
import org.example.teacherPackage.teacherService.TeacherListService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/api/teacher")
@AllArgsConstructor
public class ViewTeacherController {
    private final TeacherListService teacherListService ;

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<TeacherListResponse>> listTeachers() {
        List<TeacherListResponse> teachers = teacherListService.getTeachersByTenant();
        return ResponseEntity.ok(teachers);
    }
}
