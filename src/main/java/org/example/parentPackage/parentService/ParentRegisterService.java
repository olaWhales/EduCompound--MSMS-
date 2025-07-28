package org.example.parentPackage.parentService;

import org.example.parentPackage.dto.parentRequest.ParentRegisterRequest;
import org.example.parentPackage.dto.parentResponse.ParentRegisterResponse;

public interface ParentRegisterService {
    ParentRegisterResponse registerParent(ParentRegisterRequest request);
}
