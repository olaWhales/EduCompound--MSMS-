package org.example.studentBulkResultEntryPackage.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class StudentResultSummaryDTO {
    private UUID id;
    private UUID studentId;
    private String studentName;
    private UUID subjectId;
    private String subjectName;
    private UUID classRoomId;
    private String classRoomName;
    private UUID sessionId;
    private String sessionName;
    private String term;
    private Double totalScore;
    private String grade;
    private String status;
}