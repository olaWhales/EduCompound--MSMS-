package org.example.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SessionResponse {
    private Long sessionId;
    private String message;
    private Date createdAt;
}
