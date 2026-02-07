package org.example.data.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(
        name = "academic_sessions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"tenant_id", "session_year"})
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademicSessionEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "session_year", nullable = false)
    private String sessionYear; // e.g. 2025/2026

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private AdminTenant tenant;
}
