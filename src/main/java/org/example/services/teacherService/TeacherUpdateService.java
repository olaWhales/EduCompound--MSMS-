package org.example.services.teacherService;

import org.example.dto.requests.teacherRequest.TeacherUpdateRequest;
import org.example.dto.responses.teacherResponse.TeacherUpdateResponse;

public interface TeacherUpdateService {
    TeacherUpdateResponse updateTeacher(String email, TeacherUpdateRequest request);
}