package org.example.studentBulkResultEntryPackage.data.repository;

import org.example.studentBulkResultEntryPackage.data.model.GradingConfig;
import org.example.data.model.AdminTenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GradingConfigRepository extends JpaRepository<GradingConfig, UUID> {
    List<GradingConfig> findByTenantAndIsActiveTrue(AdminTenant tenant);
}