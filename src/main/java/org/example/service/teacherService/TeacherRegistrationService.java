package org.example.service.teacherService;

import org.example.dto.requests.TeacherRegistrationRequest;

public interface TeacherRegistrationService {
    void completeTeacherRegistration(TeacherRegistrationRequest request, String token);
}