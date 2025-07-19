package org.example.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolBranch {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    private String name; // e.g. "Main Campus", "Lekki Branch"

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private AdminTenant adminTenant;
}
