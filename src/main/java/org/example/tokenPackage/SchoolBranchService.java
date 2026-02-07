//package org.example.tokenPackage;
//
//import lombok.RequiredArgsConstructor;
//import org.example.data.model.AdminTenant;
//import org.example.data.model.SchoolBranch;
//import org.example.data.model.UserPrincipal;
//import org.example.data.repositories.SchoolBranchRepository;
//import org.example.schoolBranchPackage.data.schoolBranchRequest.SchoolBranchRequest;
//import org.example.schoolBranchPackage.data.schoolBranchResponse.SchoolBranchResponse;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class SchoolBranchService {
//
//    private final SchoolBranchRepository branchRepository;
//
//    public SchoolBranchResponse createBranch(SchoolBranchRequest request) {
//        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        AdminTenant tenant = principal.users().getAdminTenant();
//
//        if (branchRepository.existsByAdminTenantAndName(tenant, request.getName())) {throw new IllegalArgumentException("Branch with this name already exists.");}
//        SchoolBranch branch = SchoolBranch.builder()
//                .name(request.getName())
//                .adminTenant(tenant)
//                .build();
//        branchRepository.save(branch);
//
//        return SchoolBranchResponse.builder()
//                .id(branch.getId())
//                .name(branch.getName())
//                .message("Branch created successfully")
//                .build();
//    }
//}
