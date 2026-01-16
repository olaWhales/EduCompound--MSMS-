package org.example.sessionPackage.sessionService;

import org.example.data.model.AdminTenant;
import org.example.data.model.SchoolBranch;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SessionValidatorImpl implements SessionValidator {

    @Override
    public void validateDates(Date start, Date end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }
        if (start.after(end)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    @Override
    public void validateBranchOwnership(SchoolBranch branch, AdminTenant tenant) {
        if (branch != null && !branch.getAdminTenant().equals(tenant)) {
            throw new IllegalArgumentException("Branch does not belong to your school");
        }
    }
}
