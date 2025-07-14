package org.example.services.teacherService;

import org.example.dto.requests.teacherRequest.TeacherRegistrationRequest;

public interface TeacherRegistrationService {
    void completeTeacherRegistration(TeacherRegistrationRequest request, String token);
}