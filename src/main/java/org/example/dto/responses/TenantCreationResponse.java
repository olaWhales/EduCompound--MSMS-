package org.example.dto.responses;


import lombok.*;

import java.time.LocalDate;
import java.util.Date;

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
}
