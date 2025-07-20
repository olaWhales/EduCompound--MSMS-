package org.example.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ClassResponse {
//    private Long classId;
    private String className;
    private String sessionYear;
    private String term;
    private String message;
}
