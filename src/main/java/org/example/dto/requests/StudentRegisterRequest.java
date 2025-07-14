package org.example.dto.requests;

import lombok.Data;

@Data
public class StudentRegisterRequest {
    private String firstName;
    private String lastName;
    private Long classId;
    private Long sessionId;
    private Long parentUserId; // Optional: If the parent exists in the system

}
