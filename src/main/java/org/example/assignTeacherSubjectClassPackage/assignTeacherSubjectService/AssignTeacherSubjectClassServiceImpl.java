package org.example.assignTeacherSubjectClassPackage.assignTeacherSubjectService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.*;
import org.example.data.repositories.*;
import org.example.assignTeacherSubjectClassPackage.dto.assignTeacherSubjectRequest.AssignTeacherSubjectClassRequest;
import org.example.assignTeacherSubjectClassPackage.dto.assignTeacherSubjectResponse.AssignTeacherSubjectClassResponse;
import org.example.utilities.TenantSecurityUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignTeacherSubjectClassServiceImpl implements AssignTeacherSubjectClassService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final ClassRoomRepository classRoomRepository;
    private final TeacherSubjectClassRepository teacherSubjectClassRepository;
    private final TenantSecurityUtil tenantSecurityUtil;
    private final UserRepository userRepository ;

    @Override
    public AssignTeacherSubjectClassResponse assign(AssignTeacherSubjectClassRequest request) {
        AdminTenant adminTenant = tenantSecurityUtil.getAuthenticatedTenant();

        Users teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        ClassRoom classRoom = classRoomRepository.findById(request.getClassRoomId())
                .orElseThrow(() -> new IllegalArgumentException("ClassRoom not found"));

        TeacherSubjectClass mapping = TeacherSubjectClass.builder()
                .teacher(teacher)
                .subject(subject)
                .classRoom(classRoom)
                .tenant(adminTenant)
                .build();

        TeacherSubjectClass saved = teacherSubjectClassRepository.save(mapping);

        AssignTeacherSubjectClassResponse response = new AssignTeacherSubjectClassResponse();
        response.setId(saved.getId());
        response.setTeacherId(teacher.getUserId());
        response.setSubjectId(subject.getId());
        response.setClassRoomId(classRoom.getClassId());

        return response;
    }
}
