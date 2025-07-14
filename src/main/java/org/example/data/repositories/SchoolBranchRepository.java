package org.example.data.repositories;

import org.example.data.model.AdminTenant;
import org.example.data.model.SchoolBranch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolBranchRepository extends JpaRepository<SchoolBranch, Long> {
    List<SchoolBranch> findByAdminTenant(AdminTenant tenant);
    boolean existsByAdminTenantAndName(AdminTenant tenant, String name);
}
