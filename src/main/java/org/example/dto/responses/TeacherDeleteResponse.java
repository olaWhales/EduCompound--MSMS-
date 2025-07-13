package org.example.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherDeleteResponse {
    private String message;
}