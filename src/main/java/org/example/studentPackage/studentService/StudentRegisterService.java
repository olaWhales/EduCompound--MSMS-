package org.example.studentPackage.studentService;

import org.example.studentPackage.dto.studentRequest.StudentRegisterRequest;
import org.example.studentPackage.dto.studentResponse.StudentRegisterResponse;

public interface StudentRegisterService {
    StudentRegisterResponse studentRegistrationResponse(StudentRegisterRequest studentRegisterRequest);
}
