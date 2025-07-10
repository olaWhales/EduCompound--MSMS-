package org.example.service;

import org.example.dto.requests.AdminInitiateTeacher;

public interface TeacherInvitationService {
    void initiateTeacherRegistration(AdminInitiateTeacher request);
}