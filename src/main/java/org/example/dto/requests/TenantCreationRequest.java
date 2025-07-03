package org.example.dto.requests;

import lombok.Data;

@Data
public class TenantCreationRequest {
    private String schoolName;
    private String subdomain;
    private String adminEmail;
    private String adminPassword;
    private String adminFirstName;
    private String adminLastName;

}
