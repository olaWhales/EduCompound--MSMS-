package org.example.studentBulkResultEntryPackage.data.repository;

import org.example.studentBulkResultEntryPackage.data.model.StudentResult;
import org.example.data.model.AdminTenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentResultRepository extends JpaRepository<StudentResult, UUID> {
    List<StudentResult> findByAdminTenantAndStatus(AdminTenant tenant, StudentResult.ResultStatus status);
}