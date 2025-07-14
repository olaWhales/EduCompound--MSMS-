package org.example.services.studentService;

import org.example.dto.requests.StudentRegisterRequest;
import org.example.dto.responses.StudentRegisterResponse;

public interface StudentRegisterService {
    StudentRegisterResponse studentRegistrationResponse(StudentRegisterRequest studentRegisterRequest);
}
