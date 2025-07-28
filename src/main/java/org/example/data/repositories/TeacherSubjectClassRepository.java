package org.example.data.repositories;

import org.example.data.model.TeacherSubjectClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface TeacherSubjectClassRepository extends JpaRepository<TeacherSubjectClass, UUID> {
    List<TeacherSubjectClass> findByTenant_TenantId(UUID tenantId);
}
