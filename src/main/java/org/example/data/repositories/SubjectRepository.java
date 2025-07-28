package org.example.data.repositories;

import org.example.data.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {
    List<Subject> findByTenant_TenantId(UUID tenantId);

    Optional<Subject> findByIdAndTenant_TenantId(UUID subjectId, UUID tenantId);
}
