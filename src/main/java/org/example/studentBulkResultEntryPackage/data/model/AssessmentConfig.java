package org.example.studentBulkResultEntryPackage.data.model;

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
public class AssessmentConfig {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @NotBlank(message = "Assessment name is mandatory")
    private String name; // e.g., CA1, Test1

    @NotNull(message = "Weight is mandatory")
    @Min(value = 0, message = "Weight must be non-negative")
    private Integer weight;

    @NotNull(message = "Required status is mandatory")
    private Boolean isRequired;

    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private AdminTenant tenant;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}