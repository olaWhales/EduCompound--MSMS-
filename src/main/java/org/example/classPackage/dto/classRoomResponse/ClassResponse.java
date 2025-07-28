package org.example.classPackage.dto.classRoomResponse;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ClassResponse {
    private UUID classId;
    private String className;
    private String sessionYear;
    private String term;
    private String message;
}
