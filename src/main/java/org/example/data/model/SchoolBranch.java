package org.example.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolBranch {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    private String name; // e.g. "Main Campus", "Lekki Branch"

//    @ManyToOne
    @ManyToOne
//            (fetch = FetchType.EAGER)   // ← change from default LAZY
    @JoinColumn(name = "tenant_id", nullable = false)
    private AdminTenant adminTenant;

    // ── ADD THIS ──
    @Column(nullable = false)
    @Builder.Default
    private boolean isPrimary = false;

    // Optional: add these later for better model
    // private LocalDateTime createdAt = LocalDateTime.now();
    // private String address;
    // private String contactPhone;
}
