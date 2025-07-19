package org.example.data.repositories;

import org.example.data.model.Role;
import org.example.data.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByEmail(String email);

    List<Users> findByRole(Role role);
    List<Users> findByRoleAndAdminTenant_TenantId(Role role, UUID tenantId); // Updated to use tenantId

    boolean existsByEmail(String email);
}