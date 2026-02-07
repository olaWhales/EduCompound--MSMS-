package org.example.data.repositories;

import org.example.data.model.AcademicSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AcademicSessionRepository
        extends JpaRepository<AcademicSessionEntity, UUID> {

    boolean existsByTenant_TenantIdAndSessionYear(
            UUID tenantId,
            String sessionYear
    );

    Optional<AcademicSessionEntity> findByTenant_TenantIdAndSessionYear(
            UUID tenantId,
            String sessionYear
    );
}
