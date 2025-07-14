package org.example.dto.responses.teacherResponse;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TeacherUpdateResponse {
    private String message;
    private Date updatedAt;
}