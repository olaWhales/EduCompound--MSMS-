package org.example.data.repositories;

import org.example.data.model.AdminTenant;
import org.example.data.model.ClassRoom;
import org.example.data.model.Term;
import org.example.data.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsByStudentCodeAndAdminTenant(String studentCode, AdminTenant tenant);

//    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndMiddleNameIgnoreCaseAndAdminTenantAndSession(String trim, String trim1, String s, AdminTenant tenant, Term term);

    Optional<Student> findByStudentCodeAndAdminTenant(String studentCode, AdminTenant tenant);

    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndAdminTenant(
            String firstName,
            String lastName,
            AdminTenant adminTenant
    );

//    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndMiddleNameIgnoreCaseAndAdminTenantAndSessionAndClassRoom(
//            String firstName,
//            String lastName,
//            String middleName,
//            AdminTenant adminTenant,
//            Term term,
//            ClassRoom classRoom
//    );

    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndMiddleNameIgnoreCaseAndAdminTenantAndTermAndClassRoom(
            String firstName,
            String lastName,
            String middleName,
            AdminTenant adminTenant,
            Term term,
            ClassRoom classRoom
    );



//    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndMiddleNameIgnoreCaseAndAdminTenantAndTermAndClassRoom(
//            String trim, String trim1, String s, AdminTenant tenant, Term term, ClassRoom classRoom);
}
