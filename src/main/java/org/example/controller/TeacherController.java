package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.requests.AdminInitiateTeacher;
import org.example.dto.requests.TeacherRegistrationRequest;
import org.example.service.TeacherInvitationService;
import org.example.service.teacherService.TeacherRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/api/teacher")
@AllArgsConstructor
public class TeacherController {
    private final TeacherInvitationService teacherInvitationService;
    private final TeacherRegistrationService teacherRegistrationService;

    @PostMapping("/invite")
    public ResponseEntity<String> inviteTeacher(@RequestBody AdminInitiateTeacher request) {
        teacherInvitationService.initiateTeacherRegistration(request);
        return ResponseEntity.ok("Invitation sent successfully");
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> completeTeacherRegistration(
//            @Valid @RequestBody TeacherRegistrationRequest request,
//            @RequestParam String token) {
//        teacherRegistrationService.completeTeacherRegistration(request, token);
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Teacher registered successfully");
//        response.put("redirect", "/api/teacher/register/teacher?success=true");
//        return ResponseEntity.ok("Teacher registered successfully");
//    }
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
}