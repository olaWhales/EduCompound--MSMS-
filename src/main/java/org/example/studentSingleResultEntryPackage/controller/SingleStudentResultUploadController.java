//package org.example.studentSingleResultEntryPackage.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.example.data.model.UserPrincipal;
//import org.example.studentBulkResultEntryPackage.service.StudentResultEntryService;
//import org.example.studentSingleResultEntryPackage.dto.request.SingleStudentResultRequest;
//import org.example.studentSingleResultEntryPackage.dto.response.SingleStudentResultResponse;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/results")
//@RequiredArgsConstructor
//public class SingleStudentResultUploadController {
//
//    private final StudentResultEntryService studentResultService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<SingleStudentResultResponse> uploadResult(@RequestBody SingleStudentResultRequest request,
//                                                                    @AuthenticationPrincipal UserPrincipal principal) {
//        UUID tenantId = principal.users().getUserId(); // Adjust based on your security context
//        SingleStudentResultResponse response = studentResultService.uploadResult(request, tenantId);
//        return ResponseEntity.ok(response);
//    }
//}

