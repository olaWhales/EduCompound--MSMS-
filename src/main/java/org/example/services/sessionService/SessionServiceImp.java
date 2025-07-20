//package org.example.services.sessionService;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.example.data.model.*;
//import org.example.data.repositories.SchoolBranchRepository;
//import org.example.data.repositories.SessionRepository;
//import org.example.dto.requests.SessionRequest;
//import org.example.dto.responses.SessionResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//@RequiredArgsConstructor
//public class SessionServiceImp implements SessionService {
//
//    private final SessionRepository sessionRepository;
//    private final SchoolBranchRepository branchRepository;
//
//    @Transactional
//    public SessionResponse sessionResponse(SessionRequest sessionRequest) {
//        // Validate start and end dates
//        if (sessionRequest.getStartDate() == null || sessionRequest.getEndDate() == null) {
//            throw new IllegalArgumentException("Start date and end date are required");
//        }
//        if (sessionRequest.getStartDate().after(sessionRequest.getEndDate())) {
//            throw new IllegalArgumentException("Start date must be before end date");
//        }
//
//
//
//        // Get current user and tenant
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
//        Users currentUser = principal.users();
//        AdminTenant tenant = currentUser.getAdminTenant();
//
//        // Handle optional branch
//        SchoolBranch branch = null;
//        if (sessionRequest.getBranchId() != null) {
//            branch = branchRepository.findById(sessionRequest.getBranchId())
//                    .orElseThrow(() -> new IllegalArgumentException("Branch not found"));
//
//            if (!branch.getAdminTenant().equals(tenant)) {
//                throw new IllegalArgumentException("Branch does not belong to your school.");
//            }
//        }
//        if (sessionRepository.existsByTenantIdAndIsActiveTrue(tenantId)) {
//            sessionRepository.deactivateAllForTenant(tenantId); // write a custom JPQL update
//        }
//
//
//        // Check for duplicate session for this tenant and (optional) branch
//        boolean exists = sessionRepository.existsByAdminTenantAndStartDateAndEndDateAndSchoolBranch(
//                tenant,
//                sessionRequest.getStartDate(),
//                sessionRequest.getEndDate(),
//                branch
//        );
//
//        if (exists) {
//            throw new IllegalArgumentException("A session with the same dates already exists for this tenant/branch.");
//        }
//
//        // Create and save session
//        Session session = Session.builder()
//                .adminTenant(tenant)
//                .schoolBranch(branch) // can be null
//                .isActive(true)
//                .sessionYear(sessionRequest.getSessionYear())
//                .term(sessionRequest.getTerm())
//                .startDate(sessionRequest.getStartDate())
//                .endDate(sessionRequest.getEndDate())
//                .build();
//        sessionRepository.save(session);
//
//        return SessionResponse.builder()
//                .sessionId(session.getSessionId())
//                .message("Session created successfully")
//                .createdAt(new Date())
//                .build();
//    }
//}


package org.example.services.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.*;
import org.example.data.repositories.SchoolBranchRepository;
import org.example.data.repositories.SessionRepository;
import org.example.dto.requests.SessionRequest;
import org.example.dto.responses.SessionResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class SessionServiceImp implements SessionService {

    private final SessionRepository sessionRepository;
    private final SchoolBranchRepository branchRepository;

    @Transactional
    public SessionResponse sessionResponse(SessionRequest sessionRequest) {
        // 1. Validate dates
        if (sessionRequest.getStartDate() == null || sessionRequest.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date are required");
        }
        if (sessionRequest.getStartDate().after(sessionRequest.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        // 2. Get authenticated user and tenant
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        Users currentUser = principal.users();
        AdminTenant tenant = currentUser.getAdminTenant();

        // 3. Optional branch
        SchoolBranch branch = null;
        if (sessionRequest.getBranchId() != null) {
            branch = branchRepository.findById(sessionRequest.getBranchId())
                    .orElseThrow(() -> new IllegalArgumentException("Branch not found"));
            if (!branch.getAdminTenant().equals(tenant)) {
                throw new IllegalArgumentException("Branch does not belong to your school.");
            }
        }

        // 4. Deactivate existing session (branch-aware)
        if (branch != null) {
            sessionRepository.deactivateAllForTenantAndBranch(tenant.getTenantId(), branch);
        } else {
            sessionRepository.deactivateAllForTenant(tenant.getTenantId()); // fallback
        }

        // 5. Check for duplicate session (same dates + branch)
        boolean exists = sessionRepository.existsByAdminTenantAndStartDateAndEndDateAndSchoolBranch(
                tenant,
                sessionRequest.getStartDate(),
                sessionRequest.getEndDate(),
                branch
        );
        if (exists) {
            throw new IllegalArgumentException("A session with the same dates already exists for this tenant/branch.");
        }

        // 6. Save new session
        Session session = Session.builder()
                .adminTenant(tenant)
                .schoolBranch(branch) // can be null
                .isActive(true)
                .sessionYear(sessionRequest.getSessionYear())
                .term(sessionRequest.getTerm())
                .startDate(sessionRequest.getStartDate())
                .endDate(sessionRequest.getEndDate())
                .build();
        sessionRepository.save(session);

        // 7. Response
        return SessionResponse.builder()
//                .sessionId(session.getSessionId())
                .message("Session created successfully")
                .createdAt(new Date())
                .build();
    }
}
