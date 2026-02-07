package org.example.sessionPackage.dto.sessionResponse;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AcademicSessionResponse {

    private UUID academicSessionId;
    private String sessionYear;
    private UUID tenantId;
    private String message;
}
