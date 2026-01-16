package org.example.teacherPackage.dto.teacherResponse;


import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantCreationResponse {
    private String schoolName;
    private String subDomain;
    private Date dateCreated;
    private String message ;
    private UUID tenantId ;
}
