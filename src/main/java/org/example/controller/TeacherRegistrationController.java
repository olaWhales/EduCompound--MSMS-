//package org.example.controller;
//
//import lombok.AllArgsConstructor;
//import org.example.dto.requests.TeacherRegistrationRequest;
//import org.example.service.teacherService.TeacherRegistrationService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/teacher")
//@AllArgsConstructor
//public class TeacherRegistrationController {
//    private final TeacherRegistrationService teacherRegistrationService;
//
//    @PostMapping("/register")
//    public ResponseEntity<String> completeTeacherRegistration(
//            @Valid @RequestBody TeacherRegistrationRequest request,
//            @RequestParam String token) {
//        teacherRegistrationService.completeTeacherRegistration(request, token);
//        return ResponseEntity.ok("Teacher registered successfully");
//    }
//}
