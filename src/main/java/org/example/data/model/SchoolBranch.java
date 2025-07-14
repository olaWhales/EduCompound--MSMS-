package org.example.data.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g. "Main Campus", "Lekki Branch"

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private AdminTenant adminTenant;
}
