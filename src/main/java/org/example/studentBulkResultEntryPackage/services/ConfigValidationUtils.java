package org.example.studentBulkResultEntryPackage.services;

import org.example.data.repositories.AdminTenantRepository;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.data.model.ExamConfig;

import java.util.UUID;

public class ConfigValidationUtils {

    public static void validateTenant(UUID tenantId, AdminTenantRepository adminTenantRepository) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }
        adminTenantRepository.findById(tenantId).orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));}

    public static void validateAssessmentConfig(AssessmentConfig assessmentConfig) {
        if (assessmentConfig == null) {throw new IllegalArgumentException("Assessment config cannot be null");}
        if (assessmentConfig.getName() == null || assessmentConfig.getName().trim().isEmpty()) {throw new IllegalArgumentException("Assessment config name cannot be empty");}
        if (assessmentConfig.getWeight() == null || assessmentConfig.getWeight() < 0) {throw new IllegalArgumentException("Assessment config weight must be non-negative");}
        if (assessmentConfig.getIsRequired() == null) {throw new IllegalArgumentException("Assessment config required status cannot be null");}
    }
    public static void validateExamConfig(ExamConfig examConfig) {
        if (examConfig == null) {throw new IllegalArgumentException("Exam config cannot be null");}
        if (examConfig.getName() == null || examConfig.getName().trim().isEmpty()) {throw new IllegalArgumentException("Exam config name cannot be empty");}
        if (examConfig.getWeight() == null || examConfig.getWeight() < 0) {throw new IllegalArgumentException("Exam config weight must be non-negative");}
        if (examConfig.getIsRequired() == null) {throw new IllegalArgumentException("Exam config required status cannot be null");}
    }
}