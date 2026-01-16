package org.example.studentPackage.dto.studentRequest;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.data.model.Gender;

import java.util.UUID;

@Data
public class StudentRegisterRequest {

    @NotNull(message = "Firstname is required")
    private String firstName;
    @NotNull(message = "Lastname is required")
    private String lastName;
    @NotNull(message = "Middlename is required")
    private String middleName;
    @NotNull(message = "Gender is required")
    private Gender gender;
    @NotNull(message = "Class name is required")
    private String className;
    private Boolean activateLogin; // admin choice

    private UUID branchId;

    private Boolean createLoginAccount;
    @Email(message = "Invalid email format")
    private String studentEmail;
    private String password;
    @AssertTrue(message = "studentEmail and password are required when createLoginAccount is true")
    public boolean isLoginDataValid() {
        if (Boolean.TRUE.equals(createLoginAccount)) {
            return studentEmail != null && !studentEmail.isBlank()
                    && password != null && !password.isBlank();
        }
        return true;
    }
}
