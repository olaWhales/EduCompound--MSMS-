package org.example.service;

import org.example.dto.requests.TenantCreationRequest;
import org.example.dto.responses.TenantCreationResponse;

public interface TenantService {
    TenantCreationResponse createTenant(TenantCreationRequest request);
}
