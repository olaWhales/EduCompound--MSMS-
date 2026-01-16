package org.example.sessionPackage.sessionService;

import org.example.data.model.Session;

import java.util.List;
import java.util.UUID;

public interface SessionQueryService {
    List<Session> findAll();
    Session findById(UUID sessionId);
}
