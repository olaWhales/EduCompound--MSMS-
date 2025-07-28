package org.example.subjectPackage.subjectService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.model.Subject;
import org.example.data.repositories.SubjectRepository;
import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ViewSubjectServiceImpl implements ViewSubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final TenantSecurityUtil tenantSecurityUtil;

    @Override
    public List<SubjectResponseDTO> getAllSubjects() {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();
        List<Subject> subjects = subjectRepository.findByTenant_TenantId(tenant.getTenantId());

        return subjects.stream()
                .map(subjectMapper::toResponse)
                .toList();
    }
}
