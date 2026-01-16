package org.example.subjectPackage.subjectService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.model.ClassRoom;
import org.example.data.model.Subject;
import org.example.data.repositories.ClassRoomRepository;
import org.example.data.repositories.SubjectRepository;
import org.example.subjectPackage.dto.SubjectRequest.SubjectRequestDTO;
import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static org.example.utilities.Utilities.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateSubjectServiceImp implements CreateSubjectService {

    private final SubjectRepository subjectRepository;
    private final ClassRoomRepository classRoomRepository;
    private final SubjectMapper subjectMapper;
    private final TenantSecurityUtil tenantSecurityUtil;

    @Override
    public SubjectResponseDTO createSubject(SubjectRequestDTO request) {
        AdminTenant tenant = tenantSecurityUtil.getAuthenticatedTenant();

        // Find all classes with the given names that belong to the authenticated tenant
        List<String> classNames = request.getClassNames();
        Set<ClassRoom> validateClassRooms = classRoomRepository
                .findByClassNameInAndAdminTenant_TenantId(classNames, tenant.getTenantId());

        if (validateClassRooms.isEmpty()) {
            throw new IllegalArgumentException(NO_MATCHING_CLASS_FOUND_FOR_THE_GIVEN_NAMES_UNDER_THIS_TENANT);
        }
        Subject subject = Subject.builder()
                .name(request.getName())
                .description(request.getDescription())
                .assignedClasses(validateClassRooms.stream().toList())
                .tenant(tenant)
                .build();

        subjectRepository.save(subject);
        return subjectMapper.toResponse(subject);
    }
}
