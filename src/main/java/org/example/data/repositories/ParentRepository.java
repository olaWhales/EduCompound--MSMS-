package org.example.data.repositories;

import org.example.data.model.AdminTenant;
import org.example.data.model.Parent;
import org.example.data.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParentRepository extends JpaRepository<Parent, UUID> {
    Optional<Parent> findByUsers(Users parentUser);

    boolean existsByUsersAndAdminTenant(Users user, AdminTenant tenant);
}
