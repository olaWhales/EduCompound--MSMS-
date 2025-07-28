package org.example.sessionPackage.sessionService;

import org.example.sessionPackage.dto.sessionRequest.SessionRequest;
import org.example.sessionPackage.dto.sessionResponse.SessionResponse;

public interface SessionService {
    SessionResponse sessionResponse(SessionRequest sessionRequest);
}
