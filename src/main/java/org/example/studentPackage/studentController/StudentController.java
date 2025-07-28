package org.example.studentPackage.studentController;

import lombok.RequiredArgsConstructor;
import org.example.studentPackage.dto.studentRequest.StudentRegisterRequest;
import org.example.studentPackage.dto.studentResponse.StudentRegisterResponse;
import org.example.studentPackage.studentService.StudentRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRegisterService studentService;

//    @Operation(summary = "Register a new student")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Student registered successfully",
//                    content = @Content(schema = @Schema(implementation = StudentRegisterResponse.class))),
//            @ApiResponse(responseCode = "400", description = "Invalid request data",
//                    content = @Content(schema = @Schema(implementation = String.class)))
//    })
    @PostMapping("/student/register")
    public ResponseEntity<?> registerStudent(@RequestBody StudentRegisterRequest request) {
        try {
            StudentRegisterResponse response = studentService.studentRegistrationResponse(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
}
