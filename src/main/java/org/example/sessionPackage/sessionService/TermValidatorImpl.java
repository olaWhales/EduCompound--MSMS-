package org.example.sessionPackage.sessionService;

//import org.example.data.model.AdminTenant;
//import org.example.data.model.SchoolBranch;
//import org.example.utilities.Utilities;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.Date;

//@Component
//public class SessionValidatorImpl implements SessionValidator {
//    private static final int MAX_PAST_MONTHS_FOR_START = 3; // 3 months before today
//
//
//    @Override
//    public void validateDates(Date start, Date end) {
//        if (start == null && end == null) {throw new IllegalArgumentException("Start date and end date are required.");}
//        if (start == null) {throw new IllegalArgumentException("Start date is required.");}
//        if (end == null) {throw new IllegalArgumentException("End date is required.");}
//
//        LocalDate startDate = toLocalDate(start);
//        LocalDate endDate = toLocalDate(end);
//        LocalDate startLocal = convertToLocalDate(start);
//        LocalDate endLocal = convertToLocalDate(end);
//        LocalDate today = LocalDate.now(ZoneId.systemDefault());
//        LocalDate earliestAllowed = today.minusMonths(MAX_PAST_MONTHS_FOR_START);
//
//        // Check year
//        if (startDate.getYear() > endDate.getYear()) {throw new IllegalArgumentException("Start year must be before or equal to end year.");}
//        // Check month within same year
//        if (startDate.getYear() == endDate.getYear() && startDate.getMonthValue() > endDate.getMonthValue()) {throw new IllegalArgumentException("Start month must be before or equal to end month.");}
//
//        // 1️⃣ Start date cannot be before allowed 3-month window
//        if (startLocal.isBefore(earliestAllowed)) {
//            throw new IllegalArgumentException(
//                    String.format("Start date cannot be more than %d months in the past. Earliest allowed: %s",
//                            MAX_PAST_MONTHS_FOR_START, earliestAllowed)
//            );
//        }
//
//        // 2️⃣ Start date cannot be after today (unless future sessions are allowed)
//        if (startLocal.isAfter(today)) {
//            throw new IllegalArgumentException("Start date cannot be in the future: " + startLocal);
//        }
//
//        // 3️⃣ End date must be after start date
//        if (endLocal.isBefore(startLocal)) {
//            throw new IllegalArgumentException(
//                    String.format("End date (%s) must be after start date (%s)", endLocal, startLocal)
//            );
//        }
//
//        // 4️⃣ End date cannot be more than 1 year from start date
//        if (endLocal.isAfter(startLocal.plusYears(1))) {
//            throw new IllegalArgumentException(
//                    String.format("End date (%s) cannot be more than 1 year after the start date (%s)",
//                            endLocal, startLocal)
//            );
//        }
//
//
////        // Check day within same month/year
////        if (startDate.getYear() == endDate.getYear() &&
////                startDate.getMonthValue() == endDate.getMonthValue() &&
////                startDate.getDayOfMonth() > endDate.getDayOfMonth()) {
////            throw new IllegalArgumentException("Start day must be before or equal to end day.");
////        }
//    }
//    @Override
//    public void validateBranchOwnership(SchoolBranch branch, AdminTenant tenant) {
//        if (branch != null && !branch.getAdminTenant().equals(tenant)) {
//            throw new IllegalArgumentException(Utilities.BRANCH_DOES_NOT_BELONG_TO_YOUR_SCHOOL);
//        }
//    }
//
//    // Helper to convert java.util.Date to java.time.LocalDate
//    private LocalDate toLocalDate(Date date) {
//        return Instant.ofEpochMilli(date.getTime())
//                .atZone(ZoneId.systemDefault())
//                .toLocalDate();
//    }
//
//    private LocalDate convertToLocalDate(Date date) {
//        return date.toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDate();
//    }
//}

import org.example.data.model.AdminTenant;
import org.example.data.model.SchoolBranch;
import org.example.data.model.Term;
import org.example.data.repositories.TermRepository;
import org.example.utilities.Utilities;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class TermValidatorImpl implements TermValidator {

    private static final int MAX_PAST_MONTHS_FOR_START = 3;        // start date grace
    private static final int MAX_SESSION_DURATION_YEARS = 1;       // max 1 year
    private static final int MAX_FUTURE_DAYS_FOR_START = 31;       // can plan 31 days ahead

    private final TermRepository termRepository;

    public TermValidatorImpl(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    @Override
    public void validateDates(Date start, Date end, AdminTenant tenant, SchoolBranch branch, Term currentTerm) {
        // 0️⃣ Null checks
        if (start == null && end == null) {
            throw new IllegalArgumentException("Start date and end date are required.");
        }
        if (start == null) {
            throw new IllegalArgumentException("Start date is required.");
        }
        if (end == null) {
            throw new IllegalArgumentException("End date is required.");
        }

        // 1️⃣ Convert Dates to LocalDate once
        LocalDate startLocal = toLocalDate(start);
        LocalDate endLocal = toLocalDate(end);
        LocalDate today = LocalDate.now(ZoneId.systemDefault());

        // 2️⃣ Start date: past grace period
        LocalDate earliestAllowed = today.minusMonths(MAX_PAST_MONTHS_FOR_START);
        if (startLocal.isBefore(earliestAllowed)) {
            throw new IllegalArgumentException(String.format(
                    "Start date cannot be more than %d months in the past. Earliest allowed: %s",
                    MAX_PAST_MONTHS_FOR_START, earliestAllowed));
        }

        // 3️⃣ Start date: future window
        LocalDate latestAllowed = today.plusDays(MAX_FUTURE_DAYS_FOR_START);
        if (startLocal.isAfter(latestAllowed)) {
            throw new IllegalArgumentException(String.format(
                    "Start date cannot be more than %d days in the future. Latest allowed: %s",
                    MAX_FUTURE_DAYS_FOR_START, latestAllowed));
        }

        // 4️⃣ End date: after start date
        if (endLocal.isBefore(startLocal)) {
            throw new IllegalArgumentException(
                    String.format("End date (%s) must be after start date (%s)", endLocal, startLocal));
        }

        // 5️⃣ End date: maximum session duration
        if (endLocal.isAfter(startLocal.plusYears(MAX_SESSION_DURATION_YEARS))) {
            throw new IllegalArgumentException(String.format(
                    "End date (%s) cannot be more than %d year(s) after the start date (%s)",
                    endLocal, MAX_SESSION_DURATION_YEARS, startLocal));
        }

        // 6️⃣ Year/Month/Day logical checks
        if (startLocal.getYear() > endLocal.getYear()) {
            throw new IllegalArgumentException("Start year must be before or equal to end year.");
        }
        if (startLocal.getYear() == endLocal.getYear() && startLocal.getMonthValue() > endLocal.getMonthValue()) {
            throw new IllegalArgumentException("Start month must be before or equal to end month.");
        }
        if (startLocal.getYear() == endLocal.getYear() &&
                startLocal.getMonthValue() == endLocal.getMonthValue() &&
                startLocal.getDayOfMonth() > endLocal.getDayOfMonth()) {
            throw new IllegalArgumentException("Start day must be before or equal to end day.");
        }

//        // 7️⃣ Overlapping sessions
//        List<Session> overlappingSessions = sessionRepository.findOverlappingSessions(
//                tenant.getTenantId(),
//                branch != null ? branch.getId() : null,
//                start, end
//        );
//
//        if (currentSession != null) {
//            overlappingSessions.removeIf(s -> s.getSessionId().equals(currentSession.getSessionId()));
//        }
//
//        if (!overlappingSessions.isEmpty()) {
//            throw new IllegalArgumentException("Session dates overlap with existing session(s).");
//        }
        List<Term> overlappingTerms = termRepository.findActiveOrPlannedOverlappingSessions(
                tenant.getTenantId(),
                branch != null ? branch.getId() : null,
                start,
                end
        );

        if (currentTerm != null) {
            overlappingTerms.removeIf(s -> s.getTermId().equals(currentTerm.getTermId()));
        }

        if (!overlappingTerms.isEmpty()) {
            throw new IllegalArgumentException("Session dates overlap with existing session(s).");
        }

    }

    @Override
    public void validateBranchOwnership(SchoolBranch branch, AdminTenant tenant) {
        if (branch != null && !branch.getAdminTenant().equals(tenant)) {
            throw new IllegalArgumentException(Utilities.BRANCH_DOES_NOT_BELONG_TO_YOUR_SCHOOL);
        }
    }

    // Helper: single LocalDate conversion
    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
