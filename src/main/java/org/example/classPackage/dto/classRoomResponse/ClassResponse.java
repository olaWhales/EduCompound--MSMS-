package org.example.classPackage.dto.classRoomResponse;

import lombok.Builder;
import lombok.Data;
import org.example.data.model.TermType;

import java.util.UUID;

@Data
@Builder
public class ClassResponse {
    private UUID classId;
    private String className;
    private String sessionYear;
    private TermType term;
    private String message;
}
