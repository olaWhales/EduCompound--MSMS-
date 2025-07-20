package org.example.data.repositories;

import org.example.data.model.AdminTenant;
import org.example.data.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsByStudentCodeAndAdminTenant(String studentCode, AdminTenant tenant);
}
