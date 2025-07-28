package org.example.subjectPackage.subjectService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.model.Subject;
import org.example.data.repositories.SubjectRepository;
import org.example.subjectPackage.dto.SubjectRequest.UpdateSubjectRequestDTO;
import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
@Transactional
public class UpdateSubjectServiceImp implements UpdateSubjectService {

    private final SubjectRepository subjectRepository;
    private final TenantSecurityUtil tenantSecurityUtil;
    private final SubjectMapper subjectMapper;

    @Override
    public SubjectResponseDTO updateSubject(UUID subjectId, UpdateSubjectRequestDTO request) {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();

        Subject subject = subjectRepository
                .findByIdAndTenant_TenantId(subjectId, tenant.getTenantId())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found or not owned by this tenant."));

        subject.setName(request.getSubjectName());
        subject.setDescription(request.getSubjectDescription());

        Subject updated = subjectRepository.save(subject);
        return subjectMapper.toResponse(updated);
    }
}
