package org.example.data.repositories;

import org.example.data.model.AdminTenant;
import org.example.data.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdminTenantRepository extends JpaRepository<AdminTenant, UUID> {
//    List<Subject> findAllByTenant_TenantId(UUID tenantId);

//    Optional<Admin> findByEmail(String adminEmail);
}
