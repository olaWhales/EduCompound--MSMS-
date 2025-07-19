package org.example.services.parentService;

import org.example.dto.requests.ParentRegisterRequest;
import org.example.dto.responses.ParentRegisterResponse;

public interface ParentRegisterService {
    ParentRegisterResponse registerParent(ParentRegisterRequest request);
}
