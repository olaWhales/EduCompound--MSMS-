package org.example.dto.requests;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.UUID;

@Data
public class StudentRegisterRequest {
    private String firstName;
    private String lastName;
    private String middleName ;
    private String gender;
    private String className;     // e.g., "Grade 5"
//    private String sessionYear;   // e.g., "2024/2025"
    private String term;
//    private String studentCode; // 👈 Must be provided or auto-generated during registration



    private Boolean createLoginAccount; // optional - if true, email/password must be present
    private UUID branchId; // optional
//    private String parentEmail;   // Optional: used to find user + parent record

//    @Email(message = "Invalid email format")
//    @Nullable
//    private String studentEmail;
//    // Optional: if student will log in
    @Email(message = "Invalid email format")
    private String studentEmail; // Optional

    private String password; // Optional, only required if createLoginAccount is true
}
