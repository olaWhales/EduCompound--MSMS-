package org.example.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.Year;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID sessionId;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private AdminTenant adminTenant;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private SchoolBranch schoolBranch;

    private String term;
    private String sessionYear;
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

//    private boolean active;
    private Instant deletedAt;
    private UUID deletedBy;


    @Column(nullable = false)
    private boolean isActive = true;


//    public boolean hasAcademicRecords() {
//        return !students.isEmpty()
//                || !results.isEmpty()
//                || !attendances.isEmpty();
//    }

}
