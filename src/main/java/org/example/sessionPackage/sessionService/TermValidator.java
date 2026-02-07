package org.example.sessionPackage.sessionService;

import org.example.data.model.AdminTenant;
import org.example.data.model.SchoolBranch;
import org.example.data.model.Term;

import java.util.Date;

public interface TermValidator {
    void validateDates(Date start, Date end, AdminTenant tenant, SchoolBranch branch, Term currentTerm);
    void validateBranchOwnership(SchoolBranch branch, AdminTenant tenant);
}

