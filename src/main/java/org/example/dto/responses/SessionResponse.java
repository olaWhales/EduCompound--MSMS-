package org.example.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class SessionResponse {
    private UUID sessionId;
    private String message;
    private Date createdAt;
}
