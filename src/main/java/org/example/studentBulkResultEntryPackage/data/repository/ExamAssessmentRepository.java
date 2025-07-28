package org.example.studentBulkResultEntryPackage.data.repository;

import org.example.studentBulkResultEntryPackage.data.model.ExamAssessment;
import org.example.data.model.AdminTenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExamAssessmentRepository extends JpaRepository<ExamAssessment, UUID> {
}