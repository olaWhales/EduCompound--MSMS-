package org.example.data.model;


import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;

    private String schoolName;

    private String subdomain;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}