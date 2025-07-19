package org.example.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID teacherId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private AdminTenant adminTenant;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;

//    private String firstName;
//    private String lastName;

//    @Column(name = "tenant_id", insertable = false, updatable = false)
//    private Long tenantId; // Add this field if required by the schema
}