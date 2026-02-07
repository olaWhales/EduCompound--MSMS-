package org.example.sessionPackage.dto.sessionResponse;

import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SessionDTO {
    private UUID sessionId;
    private String schoolName;      // from adminTenant
    private String subdomain;       // from adminTenant
    private String term;
    private String sessionYear;
    private String startDate;       // formatted date
    private String endDate;         // formatted date
    private String status;
    private boolean active;
}
