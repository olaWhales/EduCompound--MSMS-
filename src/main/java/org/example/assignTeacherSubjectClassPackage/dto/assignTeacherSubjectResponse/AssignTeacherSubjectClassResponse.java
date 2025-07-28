package org.example.assignTeacherSubjectClassPackage.dto.assignTeacherSubjectResponse;

import lombok.Data;
import java.util.UUID;

@Data
public class AssignTeacherSubjectClassResponse {
    private UUID id;
    private UUID teacherId;
    private UUID subjectId;
    private UUID classRoomId;
}
