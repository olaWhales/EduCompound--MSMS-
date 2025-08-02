package org.example.studentBulkResultEntryPackage.controller.assessmentController;

import org.example.studentBulkResultEntryPackage.dto.request.assessmentConfigRequest.AssessmentConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.response.AssessmentGroupResponse;
import org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigUpdateService.AssessmentConfigUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class AssessmentConfigUpdateController {

    private final AssessmentConfigUpdateService assessmentConfigService;

    public AssessmentConfigUpdateController(AssessmentConfigUpdateService assessmentConfigService) {
        this.assessmentConfigService = assessmentConfigService;
    }

    /**
     * Updates an existing assessment configuration group by name
     * @param groupName Name of the assessment group (e.g., "Midterm 2025")
     * @param request Updated configurations
     * @return Updated assessment group details
     */
    @PutMapping("/assessment_active_update-configs/{groupName}")
    public ResponseEntity<?> updateAssessmentConfig(
            @PathVariable("groupName") String groupName,
            @RequestBody AssessmentConfigRequest request
    ) {
        try {
            AssessmentGroupResponse response = assessmentConfigService.updateAssessmentConfig(groupName, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Assessment group not found: " + groupName, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating assessment config: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
