package org.example.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    private String token;

    private String email;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private AdminTenant adminTenant;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

    private boolean verified;

    public boolean isExpired() {
        return expiresAt.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .isBefore(LocalDateTime.now());
    }
}