package org.example.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class StudentRegisterResponse {
    private String message;
    private UUID studentId;
    private Date createdAt;
}
