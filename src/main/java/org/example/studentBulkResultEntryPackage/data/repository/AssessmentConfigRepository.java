package org.example.studentBulkResultEntryPackage.data.repository;

import org.example.data.model.AdminTenant;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssessmentConfigRepository extends JpaRepository<AssessmentConfig, UUID> {
//    List<AssessmentConfig> findByTenantAndIsActiveTrue(AdminTenant tenant);
    Optional<AssessmentConfig> findByNameAndTenant(String name, AdminTenant tenant);
//    Optional<AssessmentConfig> findByNameAndTenantAndIdNot(String name, AdminTenant tenant, UUID id);

    @Query("SELECT COALESCE(SUM(ac.weight), 0) FROM AssessmentConfig ac WHERE ac.tenant = :tenant AND ac.isActive = true")
    Double sumWeightsByTenantAndIsActiveTrue(AdminTenant tenant); // Changed to Double for flexibility

//    List<AssessmentConfig> findByTenant(AdminTenant tenant);

    Optional<AssessmentConfig> findByIdAndTenant(UUID id, AdminTenant tenant);

    Collection<AssessmentConfig> findAllByTenantAndIsActiveTrue(AdminTenant tenant);

    Optional<AssessmentConfig> findByIdAndTenantAndIsActiveTrue(UUID id, AdminTenant tenant);

    boolean existsByGroupNameAndTenant(String name, AdminTenant tenant);

    List<AssessmentConfig> findByGroupNameAndTenant(String groupName, AdminTenant tenant);
//    Optional<AssessmentConfig> findByNameAndTypeAndTenant_TenantId(String name, UUID tenantId);

    Optional<AssessmentConfig> findByNameAndTenant_TenantId(String name, UUID tenantId);

    Optional<AssessmentConfig> findByGroupNameAndTenant_TenantId(String name, UUID tenantId);

//    List<AssessmentConfig> findByNameAndTenantId(String name, UUID tenantId);

//    int sumWeightsByTenantAndIsActiveTrue(AdminTenant tenant);
}