package org.example.services.teacherService;

import org.example.dto.responses.teacherResponse.TeacherListResponse;

import java.util.List;

public interface TeacherListService {
    List<TeacherListResponse> getTeachersByTenant();
}