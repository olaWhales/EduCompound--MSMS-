package org.example.teacherPackage.dto.teacherResponse;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TeacherUpdateResponse {
    private String message;
    private Date updatedAt;
}