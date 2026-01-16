package org.example.sessionPackage.sessionService;

import org.example.sessionPackage.dto.sessionRequest.SessionRequest;

import java.util.UUID;

public interface SessionUpdateService {
    void update(UUID sessionId, SessionRequest request);
}

