package org.example.studentBulkResultEntryPackage.services.gradingConfigService.gradeConfigDeleteService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.model.UserPrincipal;
import org.example.studentBulkResultEntryPackage.data.model.GradingConfig;
import org.example.studentBulkResultEntryPackage.data.repository.GradingConfigRepository;
import org.example.studentBulkResultEntryPackage.services.UUIDUtil;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.example.studentBulkResultEntryPackage.services.UUIDUtil.validateAndConvert;

@Service
@RequiredArgsConstructor
public class DeleteGradingConfigServiceImpl implements DeleteGradingConfigService {
    private final GradingConfigRepository gradingConfigRepository ;
    private final TenantSecurityUtil tenantSecurityUtil;

    @Override
    public void deleteConfigs(List<String> ids) {
        AdminTenant currentTenant = tenantSecurityUtil.getAuthenticatedTenant();
        UUID tenantId = currentTenant.getTenantId();
        List<UUID> uuidList = ids.stream()
                .map(UUIDUtil::validateAndConvert)
                .toList();
        List<GradingConfig> configs = gradingConfigRepository.findAllById(uuidList);
        for (GradingConfig config : configs) {
            if (!config.getTenant().getTenantId().equals(tenantId)) {
                throw new SecurityException("Unauthorized deletion attempt for grading config not owned by this tenant.");
            }
        }
        gradingConfigRepository.deleteAll(configs);
    }
}
