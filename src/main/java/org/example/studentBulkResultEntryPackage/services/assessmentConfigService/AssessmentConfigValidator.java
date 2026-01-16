package org.example.studentBulkResultEntryPackage.services.assessmentConfigService;

import org.example.studentBulkResultEntryPackage.data.model.AssessmentType;
import org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest.AssessmentConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest.ConfigurationRequest;

import java.util.List;

public class AssessmentConfigValidator {

    public static void validate(AssessmentConfigRequest request) {

        List<ConfigurationRequest> configs = request.getConfigurations();

        long caCount = configs.stream().filter(cfg -> AssessmentType.CA.equals(cfg.getType())).count();
        long examCount = configs.stream().filter(cfg -> AssessmentType.EXAM.equals(cfg.getType())).count();

        if (caCount < request.getNumberOfCAs()) {throw new IllegalArgumentException("CA settings are fewer than the number of CAs defined (" + request.getNumberOfCAs() + ")");}
        if (caCount > request.getNumberOfCAs()) {throw new IllegalArgumentException("CA settings exceed the number of CAs defined (" + request.getNumberOfCAs() + ")");}
        if (examCount < request.getNumberOfExams()) {throw new IllegalArgumentException("Exam settings are fewer than the number of Exams defined (" + request.getNumberOfExams() + ")");}
        if (examCount > request.getNumberOfExams()) {throw new IllegalArgumentException("Exam settings exceed the number of Exams defined (" + request.getNumberOfExams() + ")");}
        int totalWeight = configs.stream()
                .mapToInt(ConfigurationRequest::getWeight)
                .sum();
        if (totalWeight != 100) {throw new IllegalArgumentException("Total weight must equal 100. Found: " + totalWeight);}
        boolean hasDuplicateNames = configs.stream()
                .map(ConfigurationRequest::getName)
                .distinct()
                .count() != configs.size();
        if (hasDuplicateNames) {throw new IllegalArgumentException("Configuration contains duplicate names.");}
    }
}
