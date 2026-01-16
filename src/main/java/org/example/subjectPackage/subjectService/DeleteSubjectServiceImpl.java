package org.example.subjectPackage.subjectService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.model.Subject;
import org.example.data.repositories.SubjectRepository;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.example.utilities.Utilities.*;

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
                .orElseThrow(() -> new IllegalArgumentException(SUBJECT_NOT_FOUND_OR_NOT_OWNED_BY_THIS_TENANT));

        subjectRepository.delete(subject);
    }
}
