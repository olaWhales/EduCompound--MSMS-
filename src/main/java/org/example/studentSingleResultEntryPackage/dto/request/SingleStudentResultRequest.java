package org.example.studentSingleResultEntryPackage.dto.request;

import lombok.Data;

import java.util.UUID;
@Data
public class SingleStudentResultRequest {

    private UUID studentId;
    private UUID subjectId;
    private UUID classRoomId;
    private UUID sessionId;

    private String term; // "First Term", etc.

    private Double caScore;
    private Double examScore;
}
