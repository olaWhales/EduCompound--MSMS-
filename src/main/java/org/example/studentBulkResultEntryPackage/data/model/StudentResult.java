package org.example.studentBulkResultEntryPackage.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.data.model.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResult {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    private ClassRoom classRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @NotBlank(message = "Term is mandatory")
    private String term;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Status is mandatory")
    private ResultStatus status = ResultStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private AdminTenant adminTenant;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "studentResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContinuousAssessment> assessments;

    @OneToMany(mappedBy = "studentResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamAssessment> examAssessments;

    public enum ResultStatus {
        DRAFT, SUBMITTED, APPROVED
    }

    // Transient fields for computed values
    @Transient
    private Double totalScore;

    @Transient
    private String grade;

    // Compute total score dynamically
    public Double getTotalScore() {
        double caScore = assessments.stream()
                .filter(ca -> ca.getAssessmentConfig().getIsActive())
                .mapToDouble(ca -> ca.getScore() * ca.getAssessmentConfig().getWeight() / 100.0)
                .sum();
        double examScore = examAssessments.stream()
                .filter(ex -> ex.getExamConfig().getIsActive())
                .mapToDouble(ex -> ex.getScore() * ex.getExamConfig().getWeight() / 100.0)
                .sum();
        return caScore + examScore;
    }

    // Compute grade dynamically (requires GradingConfig service)
    public String getGrade() {
        return grade; // Set by service after computing totalScore
    }
}