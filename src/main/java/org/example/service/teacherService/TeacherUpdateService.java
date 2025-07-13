package org.example.service.teacherService;

import org.example.dto.requests.TeacherUpdateRequest;
import org.example.dto.responses.TeacherUpdateResponse;

public interface TeacherUpdateService {
    TeacherUpdateResponse updateTeacher(String email, TeacherUpdateRequest request);
}