package org.example.sessionPackage.dto.sessionResponse;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SessionResponse {
//    private UUID sessionId;
    private String message;
    private Date createdAt;
}
