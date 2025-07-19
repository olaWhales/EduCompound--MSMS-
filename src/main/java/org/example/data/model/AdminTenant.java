package org.example.data.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminTenant {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID tenantId;

    private String schoolName;

    private String subdomain;

//    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}