package org.example.services.tenantService;

import org.example.dto.requests.teacherRequest.TenantCreationRequest;
import org.example.dto.responses.teacherResponse.TenantCreationResponse;

public interface TenantRegistration {
    TenantCreationResponse createTenant(TenantCreationRequest request);
}
