package org.example.studentBulkResultEntryPackage.controller.gradingController;

import lombok.RequiredArgsConstructor;
import org.example.globalExceptionPackage.Valid;
import org.example.studentBulkResultEntryPackage.dto.request.gradingConfigRequest.GradingConfigRequest;
import org.example.studentBulkResultEntryPackage.dto.response.GradingConfigResponse;
//import org.example.studentBulkResultEntryPackage.services.gradingConfigService.getGradeByIdConfigService.GetGradingConfigByIdService;
import org.example.studentBulkResultEntryPackage.services.gradingConfigService.gradeConfigCreateService.CreateGradingConfigService;
//import org.example.studentBulkResultEntryPackage.services.gradingConfigService.gradeConfigDeleteService.DeleteGradingConfigService;
//import org.example.studentBulkResultEntryPackage.services.gradingConfigService.gradeConfigUpdateService.UpdateGradingConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GradingConfigController {

    private final CreateGradingConfigService createService;
//    private final UpdateGradingConfigService updateService;
//    private final DeleteGradingConfigService deleteService;
//    private final GetGradingConfigByIdService getByIdService;
//    private final GetAllGradingConfigsService getAllService;

    @PostMapping("/grading-config")
    public ResponseEntity<GradingConfigResponse> create(@RequestBody @Valid GradingConfigRequest request) {
        return ResponseEntity.ok(createService.createExamConfig(request));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<GradingConfigResponse> update(@PathVariable UUID id, @RequestBody @Valid Gr) {
//        return ResponseEntity.ok(updateService.updateExamConfig(id, request));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable UUID id) {
//        deleteService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<GradingConfigResponse> getById(@PathVariable UUID id) {
//        return ResponseEntity.ok(getByIdService.getById(id));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<GradingConfigResponse>> getAll() {
//        return ResponseEntity.ok(getAllService.getAll());
//    }
}
