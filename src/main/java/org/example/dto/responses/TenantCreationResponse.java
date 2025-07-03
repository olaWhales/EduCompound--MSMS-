package org.example.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class TenantCreationResponse {
    private String schoolName;
    private String subDomain;
    private Date dateCreated;
    private String message ;
}
