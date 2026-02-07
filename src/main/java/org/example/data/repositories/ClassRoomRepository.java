package org.example.data.repositories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.data.model.AdminTenant;
import org.example.data.model.ClassRoom;
import org.example.data.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, UUID> {
//    boolean existsByAdminTenantAndClassName(AdminTenant tenant, String className);

    boolean existsByAdminTenantAndTermAndClassNameIgnoreCase(AdminTenant tenant, Term term, @NotBlank(message = "Class name is required") String className);

//    Optional<ClassRoom> findByAdminTenantAndSessionAndClassNameIgnoreCase(AdminTenant tenant, Term term, String className);

    Set<ClassRoom> findByClassNameInAndAdminTenant_TenantId(List<String> classNames, UUID tenantId);

//    Optional<ClassRoom> findByAdminTenantAndTermAndClassNameIgnoreCase(AdminTenant tenant, Optional<Term> term, @NotNull(message = "Class name is required") String className);

    Optional<ClassRoom> findByAdminTenantAndTermAndClassNameIgnoreCase(
            AdminTenant tenant,
            Term term,
            String className
    );

}
