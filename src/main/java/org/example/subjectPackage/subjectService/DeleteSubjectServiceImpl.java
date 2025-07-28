package org.example.subjectPackage.subjectService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.model.Subject;
import org.example.data.repositories.SubjectRepository;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteSubjectServiceImpl implements DeleteSubjectService {

    private final SubjectRepository subjectRepository;
    private final TenantSecurityUtil tenantSecurityUtil;

    @Override
    public void deleteSubjectById(UUID subjectId) {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();

        Subject subject = subjectRepository
                .findByIdAndTenant_TenantId(subjectId, tenant.getTenantId())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found or not owned by this tenant."));

        subjectRepository.delete(subject);
    }
}
