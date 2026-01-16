package org.example.data.repositories;

import org.example.data.model.AdminTenant;
import org.example.data.model.ClassRoom;
import org.example.data.model.Session;
import org.example.data.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsByStudentCodeAndAdminTenant(String studentCode, AdminTenant tenant);

    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndMiddleNameIgnoreCaseAndAdminTenantAndSession(String trim, String trim1, String s, AdminTenant tenant, Session session);

    Optional<Student> findByStudentCodeAndAdminTenant(String studentCode, AdminTenant tenant);

    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndAdminTenant(
            String firstName,
            String lastName,
            AdminTenant adminTenant
    );

    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndMiddleNameIgnoreCaseAndAdminTenantAndSessionAndClassRoom(
            String firstName,
            String lastName,
            String middleName,
            AdminTenant adminTenant,
            Session session,
            ClassRoom classRoom
    );


}
