package org.example.studentBulkResultEntryPackage.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.data.model.AdminTenant;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assessment_config")
public class AssessmentConfig {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    // Group this config belongs to (e.g. "2024 First Term Config A")
    private String groupName;

    @NotBlank(message = "Assessment name is mandatory")
    private String name;

    @NotNull(message = "Weight is mandatory")
    @Min(value = 0, message = "Weight must be non-negative")
    private Integer weight;

    private int numberOfCAs; // e.g., 1, 2, or 3

    private int numberOfExam ;

    @JsonProperty("type")
    private AssessmentType type;

    @NotNull(message = "Required status is mandatory")
    private Boolean isRequired;

    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private AdminTenant tenant;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}