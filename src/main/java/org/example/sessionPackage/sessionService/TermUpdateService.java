package org.example.sessionPackage.sessionService;

import org.example.sessionPackage.dto.sessionRequest.TermRequest;
import org.example.sessionPackage.dto.sessionResponse.TermResponse;

import java.util.UUID;

public interface TermUpdateService {
    TermResponse update(UUID sessionId, TermRequest request);
}

