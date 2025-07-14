package org.example.dto.responses.teacherResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherDeleteResponse {
    private String message;
}