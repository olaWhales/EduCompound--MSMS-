package org.example.teacherPackage.teacherService;

import org.example.teacherPackage.dto.teacherRequest.AdminInitiateTeacher;

public interface TeacherInvitationService {
    void initiateTeacherRegistration(AdminInitiateTeacher request);
}