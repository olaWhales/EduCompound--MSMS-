package org.example.sessionPackage.sessionService;

import org.example.data.model.AcademicSessionEntity;
import org.example.sessionPackage.dto.sessionRequest.AcademicSessionCreateRequest;
import org.example.sessionPackage.dto.sessionResponse.AcademicSessionResponse;

public interface AcademicSessionCreateService {
    AcademicSessionResponse create(AcademicSessionCreateRequest request);
}
