package org.example.sessionPackage.sessionService;

import org.example.data.model.Term;
import org.example.sessionPackage.dto.sessionResponse.SessionDTO;

import java.util.List;
import java.util.UUID;

public interface TermQueryService {
    List<SessionDTO> findAll();
    Term findById(UUID sessionId);
}
