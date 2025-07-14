package org.example.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class StudentRegisterResponse {
    private String message;
    private Long studentId;
    private Date createdAt;
}
