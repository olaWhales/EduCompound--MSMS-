package org.example.assignTeacherSubjectClassPackage.dto.assignTeacherSubjectRequest;

import lombok.Data;
import java.util.UUID;

@Data
public class AssignTeacherSubjectClassRequest {
    private UUID teacherId;
    private UUID subjectId;
    private UUID classRoomId;
}
