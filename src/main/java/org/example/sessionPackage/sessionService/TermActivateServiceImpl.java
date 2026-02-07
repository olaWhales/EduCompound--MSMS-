//package org.example.sessionPackage.sessionService;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import lombok.extern.slf4j.Slf4j;
//import org.example.Authentication.AuthenticatedUserProvider;
//import org.example.data.model.*;
//import org.example.data.repositories.SessionRepository;
//import org.example.utilities.Utilities;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.stereotype.Service;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.Date;  // java.util.Date
//
//import java.time.Instant;
//import java.time.ZoneId;
//import java.time.LocalDate;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
//public class SessionActivateServiceImpl implements SessionActivateService {
//
//    private final SessionRepository sessionRepository;
//    private final TenantContextResolver tenantResolver;
//    private final AuthenticatedUserProvider authUserProvider;
//
//    @Override
//    public void activate(UUID sessionId) {
//
//        Users admin = authUserProvider.getCurrentUser();
//        AdminTenant tenant = tenantResolver.currentTenant();
//
//        Session session = sessionRepository.findById(sessionId)
//                .orElseThrow(() ->
//                        new IllegalArgumentException(Utilities.SESSION_NOT_FOUND)
//                );
//
//        // 🔐 Tenant isolation
//        if (!session.getAdminTenant().getTenantId()
//                .equals(tenant.getTenantId())) {
//            throw new IllegalArgumentException(
//                    "You cannot activate a session from another tenant"
//            );
//        }
//        // 🚫 Only PLANNED sessions can be activated
//        if (session.getStatus() != SessionStatus.PLANNED) {
//            throw new IllegalArgumentException(
//                    "Only PLANNED sessions can be activated"
//            );
//        }
//
////        Date sqlOrUtilDate = session.getStartDate();
////
////        if (sqlOrUtilDate == null) {throw new IllegalStateException("Session start date is missing");}
////
////        LocalDate startDate;
////        if (sqlOrUtilDate instanceof java.sql.Date) {
////            // Safe: java.sql.Date has toLocalDate()
////            startDate = ((java.sql.Date) sqlOrUtilDate).toLocalDate();
////        } else {
////            // Fallback for plain java.util.Date (or unexpected subclass)
////            startDate = sqlOrUtilDate.toInstant()
////                    .atZone(ZoneId.systemDefault())
////                    .toLocalDate();
////        }
////        LocalDate today = LocalDate.now(ZoneId.systemDefault());
////        if (today.isBefore(startDate)) {throw new IllegalArgumentException("Session cannot be activated before its start date");}
//        // 📅 Cannot activate before start date
//        LocalDate today = LocalDate.now(ZoneId.systemDefault());
//        LocalDate startDate = convertToLocalDate(session.getStartDate(), session.getSessionId());
//
//        log.info("Date check → Today: {} | Session start: {}", today, startDate);
//
//        if (today.isBefore(startDate)) {
//            throw new IllegalArgumentException("Session cannot be activated before its start date");
//        }
//
////        LocalDate today     = LocalDate.now(ZoneId.systemDefault());  // explicit zone is clearer
////        LocalDate startDate = LocalDate.ofInstant(session.getStartDate().toInstant());
//
//log.info("this is the date {}", today);
//
//        if (today.isBefore(startDate)) {
//            throw new IllegalArgumentException(
//                    "Session cannot be activated before its start date"
//            );
//        }
//        log.info("Today's date: {}; Start date: {}", today, startDate);
//
//        // 🔄 Close existing ACTIVE session (business rule)
//        closeExistingActiveSession(tenant, session.getSchoolBranch(), admin);
//
//        // ✅ Activate session
//        session.setStatus(SessionStatus.ACTIVE);
//        session.setActivatedAt(Instant.now());
//        session.setActivatedBy(admin.getUserId());
//
//        sessionRepository.save(session);
//    }
//
//    private LocalDate convertToLocalDate(java.util.Date date, UUID sessionId) {
//        if (date == null) {
//            log.error("Session {} has null startDate → cannot activate", sessionId);
//            throw new IllegalStateException("Session start date is missing");
//        }
//
//        if (date instanceof java.sql.Date sqlDate) {
//            return sqlDate.toLocalDate();
//        } else {
//            // Fallback for java.util.Date
//            return date.toInstant()
//                    .atZone(ZoneId.systemDefault())
//                    .toLocalDate();
//        }
//    }
//
//    private void closeExistingActiveSession(
//            AdminTenant tenant,
//            SchoolBranch branch,
//            Users admin
//    ) {
//        sessionRepository
//                .findByAdminTenantAndSchoolBranchAndStatus(
//                        tenant,
//                        branch,
//                        SessionStatus.ACTIVE
//                )
//                .ifPresent(activeSession -> {
//                    activeSession.setStatus(SessionStatus.CLOSED);
//                    activeSession.setClosedAt(Instant.now());
//                    activeSession.setClosedBy(admin.getUserId());
//                    sessionRepository.save(activeSession);
//                });
//    }
//}
package org.example.sessionPackage.sessionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Authentication.AuthenticatedUserProvider;
import org.example.data.model.*;
import org.example.data.repositories.TermRepository;
import org.example.utilities.Utilities;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TermActivateServiceImpl implements TermActivateService {

    private final TermRepository termRepository;
    private final TenantContextResolver tenantResolver;
    private final AuthenticatedUserProvider authUserProvider;

    private static final int ALLOWED_DAYS_BEFORE_START = 7;

    @Override
    public void activate(UUID sessionId) {
        Users admin = authUserProvider.getCurrentUser();
        UUID tenant = tenantResolver.currentTenantId();

        log.info("Activating session {}", sessionId);

        Term term = termRepository.findById(sessionId).orElseThrow(() -> new IllegalArgumentException(Utilities.SESSION_NOT_FOUND));

        // Tenant isolation
        if (!term.getAdminTenant().getTenantId().equals(tenant)) {throw new IllegalArgumentException("You cannot activate a session from another tenant");}
        // Only PLANNED sessions can be activated
        if (term.getStatus() != TermStatus.PLANNED) {throw new IllegalArgumentException("Only PLANNED sessions can be activated");}
        // Date validation - allow activation up to 7 days before start date
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate startDate = convertToLocalDate(term.getStartDate(), sessionId);

        log.info("Date check → Today: {} | Session start: {}", today, startDate);

        LocalDate earliestAllowed = startDate.minusDays(ALLOWED_DAYS_BEFORE_START);

        if (today.isBefore(earliestAllowed)) {throw new IllegalArgumentException(
                    String.format("Session cannot be activated more than %d days before its start date (%s)",
                            ALLOWED_DAYS_BEFORE_START, startDate)
            );
        }
        // Close any existing ACTIVE session(s)
        closeExistingActiveSessions(tenant, admin);

        // Activate the session
        term.setStatus(TermStatus.ACTIVE);
        term.setActivatedAt(Instant.now());
        term.setActivatedBy(admin.getUserId());
        termRepository.save(term);

        log.info("Session {} successfully activated by {}", admin.getUserId(), sessionId);
    }

    private LocalDate convertToLocalDate(java.util.Date date, UUID sessionId) {
        if (date == null) {
//            log.error("Session {} has null startDate → cannot activate", sessionId);
            throw new IllegalStateException("Session start date is missing");
        }

        if (date instanceof java.sql.Date sqlDate) {
//            log.debug("Converted startDate using java.sql.Date.toLocalDate()");
            return sqlDate.toLocalDate();
        } else {
//            log.debug("Converted startDate using java.util.Date → Instant → LocalDate");
            return date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
    }

private void closeExistingActiveSessions(UUID tenantId, Users admin) {
    List<Term> activeTerms = termRepository.findAllByAdminTenant_TenantIdAndStatus(
            tenantId, TermStatus.ACTIVE
    );

    for (Term activeTerm : activeTerms) {
        activeTerm.setStatus(TermStatus.COMPLETED);
        activeTerm.setClosedAt(Instant.now());
        activeTerm.setClosedBy(admin.getUserId());
    }

    if (!activeTerms.isEmpty()) {
        termRepository.saveAll(activeTerms);
        log.info("Closed {} previous active session(s) for tenant {}", activeTerms.size(), tenantId);
    }
}

}