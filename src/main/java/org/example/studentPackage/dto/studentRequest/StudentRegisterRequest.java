package org.example.studentPackage.dto.studentRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.data.model.Gender;

import java.util.UUID;

@Data
public class StudentRegisterRequest {
    private String firstName;
    private String lastName;
    private String middleName ;

    @NotNull(message = "Gender is required")
    private Gender gender;
    private String className;     // e.g., "Grade 5"

    private Boolean createLoginAccount; // optional - if true, email/password must be present
    private UUID branchId; // optional
    @Email(message = "Invalid email format")
    private String studentEmail; // Optiona
    private String password; // Optional, only required if createLoginAccount is true
}
