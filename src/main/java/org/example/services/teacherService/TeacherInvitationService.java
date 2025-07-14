package org.example.services.teacherService;

import org.example.dto.requests.AdminInitiateTeacher;

public interface TeacherInvitationService {
    void initiateTeacherRegistration(AdminInitiateTeacher request);
}