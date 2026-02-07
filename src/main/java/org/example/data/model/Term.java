package org.example.data.model;

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
public class Term {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID termId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private AdminTenant adminTenant;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private SchoolBranch schoolBranch;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TermType term;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "academic_session_id", nullable = false)
    private AcademicSessionEntity academicSession;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date startDate;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date endDate;

//    @Column(nullable = false)
//    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TermStatus status;

    /* ================== AUDIT FIELDS ================== */

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    private Instant activatedAt;
    @Column(columnDefinition = "BINARY(16)")
    private UUID activatedBy;

    private Instant closedAt;
    @Column(columnDefinition = "BINARY(16)")
    private UUID closedBy;

    private Instant deletedAt;
    @Column(columnDefinition = "BINARY(16)")
    private UUID deletedBy;

}
