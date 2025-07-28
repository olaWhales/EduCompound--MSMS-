package org.example.studentSingleResultEntryPackage.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class SingleStudentResultResponse {

    private UUID resultId;
    private UUID studentId;
    private UUID subjectId;
    private UUID classRoomId;
    private UUID sessionId;

    private String term;
    private Double caScore;
    private Double examScore;
    private Double totalScore;

    private LocalDateTime createdAt;
}
