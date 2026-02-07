package org.example.sessionPackage.sessionService;

import org.example.sessionPackage.dto.sessionResponse.TermDeletionResponse;

import java.util.UUID;

public interface TermDeleteService {
    TermDeletionResponse delete(UUID sessionId);
}

