package org.example.teacherPackage.teacherService;

import org.example.teacherPackage.dto.teacherRequest.TeacherUpdateRequest;
import org.example.teacherPackage.dto.teacherResponse.TeacherUpdateResponse;

public interface TeacherUpdateService {
    TeacherUpdateResponse updateTeacher(String email, TeacherUpdateRequest request);
}