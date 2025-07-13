package org.example.service.teacherService;

import org.example.dto.requests.AdminInitiateTeacher;

public interface TeacherInvitationService {
    void initiateTeacherRegistration(AdminInitiateTeacher request);
}