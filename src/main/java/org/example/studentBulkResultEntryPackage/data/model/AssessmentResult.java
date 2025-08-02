package org.example.studentBulkResultEntryPackage.data.model;

import jakarta.persistence.*;
import org.example.data.model.AdminTenant;
import org.example.data.model.Session;
import org.example.data.model.Student;
import org.example.data.model.Subject;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "assessment_results", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "subject_id", "assessment_config_id"})
})
public class AssessmentResult {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(optional = false)
    private Student student;

    @ManyToOne(optional = false)
    private Subject subject;

    @ManyToOne(optional = false)
    private AssessmentConfig assessmentConfig;

    @Column(nullable = false)
    private Double score;

    @ManyToOne(optional = false)
    private AdminTenant tenant;

    @ManyToOne(optional = false)
    private Session session;

    // Optional: timestamp fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and setters
}
