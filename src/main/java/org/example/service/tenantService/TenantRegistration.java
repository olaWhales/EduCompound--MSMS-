package org.example.service.tenantService;

import org.example.dto.requests.TenantCreationRequest;
import org.example.dto.responses.TenantCreationResponse;

public interface TenantRegistration {
    TenantCreationResponse createTenant(TenantCreationRequest request);
}
