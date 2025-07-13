package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.data.model.Role;
import org.example.data.model.Teacher;
import org.example.data.model.Users;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.AdminInitiateTeacher;
import org.example.dto.requests.TeacherDeleteRequest;
import org.example.dto.requests.TeacherRegistrationRequest;
//import org.example.dto.requests.TeacherUpdateRequest;
import org.example.dto.responses.TeacherDeleteResponse;
import org.example.dto.requests.TeacherUpdateRequest;
import org.example.dto.responses.TeacherListResponse;
//import org.example.dto.responses.TeacherUpdateResponse;
import org.example.dto.responses.TeacherUpdateResponse;
import org.example.service.teacherService.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/api/teacher")
@AllArgsConstructor
public class TeacherController {
    private final TeacherInvitationService teacherInvitationService;
    private final TeacherRegistrationService teacherRegistrationService;
    private final TeacherUpdateService teacherUpdateService;
    private final TeacherDeleteService teacherDeleteService;
    private final UserRepository userRepository ;
    private final TeacherListService teacherListService ;

    @PostMapping("/invite")
    public ResponseEntity<String> inviteTeacher(@RequestBody AdminInitiateTeacher request) {
        teacherInvitationService.initiateTeacherRegistration(request);
        return ResponseEntity.ok("Invitation sent successfully");
    }

@PostMapping("/register")
public ResponseEntity<Map<String, Object>> completeTeacherRegistration(
        @Valid @RequestBody TeacherRegistrationRequest request,
        @RequestParam String token) {
    teacherRegistrationService.completeTeacherRegistration(request, token);
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Teacher registered successfully");
    response.put("success", true);
    // Suggest staying on the same page or a safe redirect
    response.put("redirect", "/login"); // Preserve the current redirect
        return ResponseEntity.ok(response);
}


    @GetMapping("/register/teacher")
    public String showRegistrationForm(@RequestParam String token, @RequestParam String email, Model model) {
        model.addAttribute("token", token);
        model.addAttribute("email", email);
        model.addAttribute("registrationRequest", new TeacherRegistrationRequest());
        return "teacher-registration";
    }


    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<TeacherListResponse>> listTeachers() {
        List<TeacherListResponse> teachers = teacherListService.getTeachersByTenant();
        return ResponseEntity.ok(teachers);
    }

//    @PutMapping("/update")
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
//    public ResponseEntity<TeacherUpdateResponse> updateTeacher(
//            @RequestParam String email,
//            @Valid @RequestBody TeacherUpdateRequest request) {
//        TeacherUpdateResponse response = teacherUpdateService.updateTeacher(email, request);
//        return ResponseEntity.ok(response);
//    }
//
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

    @DeleteMapping("/delete/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<TeacherDeleteResponse> deleteTeacher(
            @Valid @RequestBody TeacherDeleteRequest request) {
        TeacherDeleteResponse response = teacherDeleteService.deleteTeacher(request);
        return ResponseEntity.ok(response);
    }
}