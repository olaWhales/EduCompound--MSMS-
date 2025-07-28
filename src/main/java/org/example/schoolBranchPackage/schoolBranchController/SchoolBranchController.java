package org.example.schoolBranchPackage.schoolBranchController;

import lombok.RequiredArgsConstructor;
import org.example.schoolBranchPackage.data.schoolBranchRequest.SchoolBranchRequest;
import org.example.schoolBranchPackage.data.schoolBranchResponse.SchoolBranchResponse;
import org.example.tokenPackage.SchoolBranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branch")
@RequiredArgsConstructor
public class SchoolBranchController {

    private final SchoolBranchService branchService;

    @PostMapping("/create")
    public ResponseEntity<SchoolBranchResponse> createBranch(@RequestBody SchoolBranchRequest request) {
        return ResponseEntity.ok(branchService.createBranch(request));
    }
}
