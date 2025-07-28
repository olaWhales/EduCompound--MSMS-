package org.example.teacherPackage.teacherService;

import org.example.teacherPackage.dto.teacherResponse.TeacherDeleteResponse;

public interface TeacherDeleteService {
    TeacherDeleteResponse deleteTeacher(String email);
}