package org.example.studentBulkResultEntryPackage.data.repository;

import jakarta.validation.constraints.NotBlank;
import org.example.studentBulkResultEntryPackage.data.model.GradingConfig;
import org.example.data.model.AdminTenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface GradingConfigRepository extends JpaRepository<GradingConfig, UUID> {
//    List<GradingConfig> findByTenantAndIsActiveTrue(AdminTenant tenant);

//    Optional<GradingConfig> findByNameAndTenant(@NotBlank(message = "Exam name is mandatory") String name, AdminTenant tenant);

    Optional<GradingConfig> findByIdAndTenant(UUID uuid, AdminTenant tenant);

    Collection<GradingConfig> findAllByTenantAndIsActiveTrue(AdminTenant tenant);

    Optional<GradingConfig> findByIdAndTenantAndIsActiveTrue(UUID id, AdminTenant tenant);

//    List<GradingConfig> findAllByTenant(AdminTenant tenant);
//    Optional<GradingConfig> findByIdAndTenant(UUID id, AdminTenant tenant);
//    List<GradingConfig> findByTenantAndIsActiveTrue(AdminTenant tenant);
//    @Query("SELECT g FROM GradingConfig g JOIN FETCH g.tenant WHERE g.id = :id")
//    Optional<GradingConfig> findByIdWithTenant(@Param("id") UUID id);


}