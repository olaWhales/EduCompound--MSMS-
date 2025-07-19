package org.example.data.repositories;

import org.example.data.model.AdminTenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminTenantRepository extends JpaRepository<AdminTenant, UUID> {

//    Optional<Admin> findByEmail(String adminEmail);
}
