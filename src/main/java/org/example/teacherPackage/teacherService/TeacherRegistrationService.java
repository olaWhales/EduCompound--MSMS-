package org.example.teacherPackage.teacherService;

import org.example.teacherPackage.dto.teacherRequest.TeacherRegistrationRequest;

public interface TeacherRegistrationService {
    void completeTeacherRegistration(TeacherRegistrationRequest request, String token);
}