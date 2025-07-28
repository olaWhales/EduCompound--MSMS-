package org.example.teacherPackage.teacherController;

import lombok.AllArgsConstructor;
import org.example.globalExceptionPackage.Valid;
import org.example.teacherPackage.dto.teacherRequest.AdminInitiateTeacher;
import org.example.teacherPackage.dto.teacherRequest.TeacherRegistrationRequest;
import org.example.teacherPackage.teacherService.TeacherInvitationService;
import org.example.teacherPackage.teacherService.TeacherRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/api/teacher")
@AllArgsConstructor
public class RegistrationTeacherController {
    private final TeacherInvitationService teacherInvitationService;
    private final TeacherRegistrationService teacherRegistrationService;

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

}