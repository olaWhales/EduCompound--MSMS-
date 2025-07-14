package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.requests.SchoolBranchRequest;
import org.example.dto.responses.SchoolBranchResponse;
import org.example.services.SchoolBranchService;
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
