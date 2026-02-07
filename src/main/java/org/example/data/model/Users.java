package org.example.data.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
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

    @Column(nullable = true) // Allow null initially, set during password setup
    private String password; // hashed, will be null until set by tenant

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "recovery_Email")
    private String recoveryEmail ;

    @Column(unique = true)
    private String studentCode;
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(nullable = false)
//    private Date createdAt;


    @Column(nullable = false)
    private boolean verified = false;

    private String phone ;

    private boolean active;  // ✅ add this field


    @Column(nullable = false)
    private Instant statusUpdatedAt;

    @PrePersist
    protected void onCreate() {
        this.statusUpdatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.statusUpdatedAt = Instant.now();
    }

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;



}
