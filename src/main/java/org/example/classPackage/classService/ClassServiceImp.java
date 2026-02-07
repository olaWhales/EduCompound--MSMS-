package org.example.classPackage.classService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.*;
import org.example.data.repositories.ClassRoomRepository;
import org.example.data.repositories.TermRepository;
import org.example.data.repositories.SchoolBranchRepository;
import org.example.classPackage.dto.classRoomRequest.ClassRequest;
import org.example.classPackage.dto.classRoomResponse.ClassResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassServiceImp implements ClassService {
    private final ClassRoomRepository classRepository;
    private final TermRepository termRepository;
    private final SchoolBranchRepository branchRepository;

    @Transactional
    public ClassResponse classResponse(ClassRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        Users user = principal.users();

        AdminTenant tenant = user.getAdminTenant();
//        Term term = termRepository.findByAdminTenantAndIsActiveTrue(tenant).orElseThrow(() -> new IllegalArgumentException("No active session found for this tenant"));

//        Term term = termRepository.findByAdminTenantAndIsActiveTrue(tenant).orElseThrow(() -> new IllegalArgumentException("No active term found for tenant"));

        Term term = termRepository
                .findByAdminTenantAndStatus(tenant, TermStatus.ACTIVE)
                .orElseThrow(() -> new IllegalStateException("No active term found"));

        // Validate duplicate class name
        boolean exists = classRepository.existsByAdminTenantAndTermAndClassNameIgnoreCase(tenant, term, request.getClassName());
        if (exists) {throw new IllegalArgumentException("Class with this name already exists in the selected session");}
        // Optional branch
        SchoolBranch branch = null;
        if (request.getBranchId() != null) {
            branch = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new IllegalArgumentException("Branch not found"));
        }

        // Create and save class
        ClassRoom schoolClass = ClassRoom.builder()
                .className(request.getClassName())
                .term(term)
                .adminTenant(tenant)
                .schoolBranch(branch)
                .build();

        ClassRoom classRoom = classRepository.save(schoolClass);

        return ClassResponse.builder()
                .classId(schoolClass.getClassId())
                .className(schoolClass.getClassName())
                .sessionYear(term.getAcademicSession().getSessionYear())
                .term(term.getTerm())
                .message("Class created successfully")
                .build();
    }

}
