package org.example.service;

import org.example.dto.requests.TeacherRegistrationRequest;

public interface TeacherRegistration {
    void completeTeacherRegistration(TeacherRegistrationRequest request, String token);
}