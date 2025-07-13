package org.example.service.teacherService;

import org.example.dto.requests.TeacherDeleteRequest;
import org.example.dto.responses.TeacherDeleteResponse;

public interface TeacherDeleteService {
    TeacherDeleteResponse deleteTeacher(TeacherDeleteRequest request);
}