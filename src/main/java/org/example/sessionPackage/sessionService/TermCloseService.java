package org.example.sessionPackage.sessionService;

import java.util.UUID;

public interface TermCloseService {
    void close(UUID sessionId);
}