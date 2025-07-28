package org.example.tenantPackage.tenantService;

import org.example.teacherPackage.dto.teacherRequest.TenantCreationRequest;
import org.example.teacherPackage.dto.teacherResponse.TenantCreationResponse;

public interface TenantRegistration {
    TenantCreationResponse createTenant(TenantCreationRequest request);
}
