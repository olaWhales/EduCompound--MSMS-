package org.example.teacherPackage.dto.teacherRequest;

import lombok.*;

@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantCreationRequest {
    private String schoolName;
    private String subdomain;
    private String adminEmail;
    private String adminFirstName;
    private String adminLastName;

}
