package org.example.teacherPackage.teacherService;

import org.example.teacherPackage.dto.teacherResponse.TeacherListResponse;

import java.util.List;

public interface TeacherListService {
    List<TeacherListResponse> getTeachersByTenant();
}