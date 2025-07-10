package org.example.data.repositories;

import org.example.data.model.AdminTenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminTenantRepository extends JpaRepository<AdminTenant, Long> {

//    Optional<Admin> findByEmail(String adminEmail);
}
