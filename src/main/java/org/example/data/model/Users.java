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
public class Users {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private AdminTenant adminTenant;

    @Column(unique = true, nullable = false)
    private String email;

//    @Column(nullable = false)
//    private String password; // hashed
    @Column(nullable = true) // Allow null initially, set during password setup
    private String password; // hashed, will be null until set by tenant

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

//    i just added this for student login
    @Column(unique = true, nullable = false)
    private String username; // 👈 Will hold studentCode for students

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;
}
