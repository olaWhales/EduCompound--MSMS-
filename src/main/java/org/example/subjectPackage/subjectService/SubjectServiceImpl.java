//package org.example.services.subjectService;
//
//import lombok.RequiredArgsConstructor;
//
//import org.example.data.model.ClassRoom;
//import org.example.data.model.Subject;
//import org.example.data.repositories.SubjectRepository;
//import org.example.dto.responses.subjectResponse.SubjectResponse;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class SubjectServiceImpl implements SubjectService {
//
//    private final SubjectRepository subjectRepository;
//    private final ClassRepository classRepository;
//    private final TenantRepository tenantRepository;
//
//    @Override
//    @Transactional
//    public SubjectResponse createSubject(SubjectCreateRequest request, UUID tenantId) {
//        Tenant tenant = tenantRepository.findById(tenantId)
//                .orElseThrow(() -> new IllegalArgumentException("Tenant not found"));
//
//        List<ClassRoom> classEntities = classRepository.findAllById(request.getClassIds());
//
//        Subject subject = Subject.builder()
//                .name(request.getName())
//                .description(request.getDescription())
//                .elective(request.isElective())
//                .tenant(tenant)
//                .classes(classEntities)
//                .build();
//
//        Subject saved = subjectRepository.save(subject);
//
//        return mapToSubjectResponse(saved);
//    }
//
//    @Override
//    public List<SubjectResponse> getAllSubjectsForTenant(UUID tenantId) {
//        List<Subject> subjects = subjectRepository.findByTenantId(tenantId);
//        return subjects.stream()
//                .map(this::mapToSubjectResponse)
//                .toList();
//    }
//
//    private SubjectResponse mapToSubjectResponse(Subject subject) {
//        List<String> classNames = subject.getClasses().stream()
//                .map(ClassEntity::getName)
//                .toList();
//
//        return SubjectResponse.builder()
//                .id(subject.getId())
//                .name(subject.getName())
//                .description(subject.getDescription())
//                .isElective(subject.isElective())
//                .classNames(classNames)
//                .build();
//    }
//}
