package org.example.studentBulkResultEntryPackage.services.assessmentConfigService.assessmentConfigBulkDeletionService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.model.UserPrincipal;
import org.example.studentBulkResultEntryPackage.data.model.AssessmentConfig;
import org.example.studentBulkResultEntryPackage.data.repository.AssessmentConfigRepository;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.example.studentBulkResultEntryPackage.services.UUIDUtil.validateAndConvert;

@Service
@RequiredArgsConstructor
public class AssessmentConfigBulkDeletionServiceImpl implements AssessmentConfigBulkDeletionService {

    private final AssessmentConfigRepository assessmentConfigRepository;
    private final TenantSecurityUtil tenantSecurityUtil;


    @Override
    public void deleteConfigs(List<String> ids) {
//        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserPrincipal userPrincipal)) {throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());}
//        AdminTenant tenant = userPrincipal.getAdminTenant(); // safely extracted
        AdminTenant tenant = userPrincipal.users().getAdminTenant();
        for (String id : ids) {
            UUID uuid = validateAndConvert(id);
            AssessmentConfig config = assessmentConfigRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("Config not found with ID: " + id));
            if (!config.getTenant().getTenantId().equals(tenant.getTenantId())) {throw new IllegalArgumentException("Unauthorized to delete config with ID: " + id);}
            assessmentConfigRepository.delete(config);
        }
    }
}
