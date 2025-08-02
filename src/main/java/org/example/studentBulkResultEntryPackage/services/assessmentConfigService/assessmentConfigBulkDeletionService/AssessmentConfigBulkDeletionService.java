package org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigBulkDeletionService;

import java.util.List;

public interface AssessmentConfigBulkDeletionService {
    void deleteConfigs(List<String> ids);
}
