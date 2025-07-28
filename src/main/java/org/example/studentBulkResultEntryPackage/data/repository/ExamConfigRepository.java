package org.example.studentBulkResultEntryPackage.data.repository;

import org.example.data.model.AdminTenant;
import org.example.studentBulkResultEntryPackage.data.model.ExamConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamConfigRepository extends JpaRepository<ExamConfig, UUID> {
    List<ExamConfig> findByTenantAndIsActiveTrue(AdminTenant tenant);
    Optional<ExamConfig> findByNameAndTenant(String name, AdminTenant tenant);
    Optional<ExamConfig> findByNameAndTenantAndIdNot(String name, AdminTenant tenant, UUID id);

    @Query("SELECT SUM(ec.weight) FROM ExamConfig ec WHERE ec.tenant = :tenant AND ec.isActive = true")
    Double sumWeightsByTenantAndIsActiveTrue(AdminTenant tenant);
}