package org.example.subjectPackage.subjectService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.model.Subject;
import org.example.data.repositories.SubjectRepository;
import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ViewSingleSubjectServiceImpl implements ViewSingleSubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final TenantSecurityUtil tenantSecurityUtil;

    @Override
    public SubjectResponseDTO getSubjectById(UUID subjectId) {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();

        Subject subject = subjectRepository
                .findByIdAndTenant_TenantId(subjectId, tenant.getTenantId())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found for this tenant."));

        return subjectMapper.toResponse(subject);
    }
}
