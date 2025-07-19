package org.example.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassRoom {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID classId;

    @NotBlank(message = "Class name is required")
    private String className;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private AdminTenant adminTenant;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(optional = true)
    @JoinColumn(name = "branch_id")
    private SchoolBranch schoolBranch;
}
