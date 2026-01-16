package org.example.sessionPackage.sessionService;

import org.example.data.model.AdminTenant;
import org.example.data.model.SchoolBranch;

import java.util.Date;

public interface SessionValidator {
    void validateDates(Date start, Date end);
    void validateBranchOwnership(SchoolBranch branch, AdminTenant tenant);
}

