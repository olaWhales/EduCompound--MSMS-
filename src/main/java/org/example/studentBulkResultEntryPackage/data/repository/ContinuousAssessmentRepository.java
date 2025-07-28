package org.example.studentBulkResultEntryPackage.data.repository;

import org.example.studentBulkResultEntryPackage.data.model.ContinuousAssessment;
import org.example.data.model.AdminTenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContinuousAssessmentRepository extends JpaRepository<ContinuousAssessment, UUID> {
}