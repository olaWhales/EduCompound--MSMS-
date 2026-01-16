package org.example.studentPackage.studentController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.studentPackage.dto.studentRequest.StudentRegisterRequest;
import org.example.studentPackage.dto.studentResponse.StudentRegisterResponse;
import org.example.studentPackage.studentService.StudentRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
//public class StudentController {
//
//    private final StudentRegisterService studentService;
//
//    @PostMapping("/student/register")
//    public ResponseEntity<?> registerStudent(@RequestBody StudentRegisterRequest request) {
//        try {
//            StudentRegisterResponse response = studentService.studentRegistrationResponse(request);
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
//        }
//    }
//}


public class StudentController {

    private final StudentRegisterService studentService;

    @PostMapping("/register")
    public ResponseEntity<StudentRegisterResponse> registerStudent(
            @Valid @RequestBody StudentRegisterRequest request
    ) {
        return ResponseEntity.ok(
                studentService.studentRegistrationResponse(request)
        );
    }
}