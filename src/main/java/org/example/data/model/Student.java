package org.example.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID studentId;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private AdminTenant adminTenant;

    private String firstName;
    private String lastName;
    private String middleName;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassRoom classRoom;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Term term;

    @Column(nullable = false, unique = true)
    private String studentCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

//    @ManyToOne(optional = true)
//    @JoinColumn(name = "parent_id")
//    private Parent parent;

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

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    @JsonBackReference
    private Parent parent;



    @OneToOne(optional = true)
    @JoinColumn(name = "user_id")
    private Users users;

//    @Column(name = "term")
//    private TermType term;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

}
