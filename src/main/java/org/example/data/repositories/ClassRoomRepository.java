package org.example.data.repositories;

import jakarta.validation.constraints.NotBlank;
import org.example.data.model.AdminTenant;
import org.example.data.model.ClassRoom;
import org.example.data.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    boolean existsByAdminTenantAndClassName(AdminTenant tenant, String className);

    boolean existsByAdminTenantAndSessionAndClassNameIgnoreCase(AdminTenant tenant, Session session, @NotBlank(message = "Class name is required") String className);
}
