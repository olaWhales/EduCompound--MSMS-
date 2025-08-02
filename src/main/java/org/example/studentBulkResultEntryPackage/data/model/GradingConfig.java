package org.example.studentBulkResultEntryPackage.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.data.model.AdminTenant;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradingConfig {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @NotBlank(message = "Grade is mandatory")
    private String gradeName; // e.g., A, B, C

    @NotNull(message = "Minimum score is mandatory")
    @Min(value = 0, message = "Minimum score must be at least 0")
    @Max(value = 100, message = "Minimum score cannot exceed 100")
    private Integer minScore;

    @NotNull(message = "Maximum score is mandatory")
    @Min(value = 0, message = "Maximum score must be at least 0")
    @Max(value = 100, message = "Maximum score cannot exceed 100")
    private Integer maxScore;

    private String remark; // e.g., Excellent,  Good

    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private AdminTenant tenant;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    @PreUpdate
    private void validateScores() {
        if (minScore > maxScore) {
            throw new IllegalArgumentException("minScore cannot be greater than maxScore");
        }
    }
}