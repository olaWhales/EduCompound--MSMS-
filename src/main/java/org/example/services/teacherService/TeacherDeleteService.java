package org.example.services.teacherService;

import org.example.dto.responses.teacherResponse.TeacherDeleteResponse;

public interface TeacherDeleteService {
    TeacherDeleteResponse deleteTeacher(String email);
}