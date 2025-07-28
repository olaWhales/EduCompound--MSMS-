//package org.example.studentBulkResultEntryPackage.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class StudentResultController {
//
//    private final StudentResultService studentResultService;
//
//    /**
//     * Endpoint to upload multiple student results at once.
//     *
//     * @param tenantId UUID of the tenant (school)
//     * @param request BulkStudentResultRequest containing multiple student entries
//     * @return ResponseEntity with success and failure count
//     */
//    @PostMapping("/results/bulk")
//    public ResponseEntity<BulkStudentResultResponse> uploadBulkResults(
//            @RequestHeader("X-Tenant-ID") UUID tenantId,
//            @RequestBody BulkStudentResultRequest request
//    ) {
//        BulkStudentResultResponse response = studentResultService.uploadBulkResults(request, tenantId);
//        return ResponseEntity.ok(response);
//    }
//}
