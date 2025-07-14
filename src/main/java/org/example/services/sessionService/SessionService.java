package org.example.services.sessionService;

import org.example.dto.requests.SessionRequest;
import org.example.dto.responses.SessionResponse;

public interface SessionService {
    SessionResponse sessionResponse(SessionRequest sessionRequest);
}
