package org.example.service.teacherService;

import org.example.dto.responses.TeacherListResponse;

import java.util.List;

public interface TeacherListService {
    List<TeacherListResponse> getTeachersByTenant();
}