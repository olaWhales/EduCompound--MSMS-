//package org.example.studentSingleResultEntryPackage.service;
//
//import lombok.RequiredArgsConstructor;
//import org.example.data.model.*;
//import org.example.data.repositories.*;
//import org.example.studentSingleResultEntryPackage.data.model.StudentResult;
//import org.example.studentSingleResultEntryPackage.data.repository.StudentResultRepository;
//import org.example.studentSingleResultEntryPackage.dto.request.SingleStudentResultRequest;
//import org.example.studentSingleResultEntryPackage.dto.response.SingleStudentResultResponse;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class StudentResultServiceImpl implements StudentResultService {
//
//    private final StudentRepository studentRepository;
//    private final SubjectRepository subjectRepository;
//    private final ClassRoomRepository classRoomRepository;
//    private final SessionRepository sessionRepository;
//    private final AdminTenantRepository adminTenantRepository;
//    private final StudentResultRepository studentResultRepository;
//
//    @Override
//    public SingleStudentResultResponse uploadResult(SingleStudentResultRequest request, UUID tenantId) {
//        // Fetch related entities
//        Student student = studentRepository.findById(request.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
//        Subject subject = subjectRepository.findById(request.getSubjectId()).orElseThrow(() -> new RuntimeException("Subject not found"));
//        ClassRoom classRoom = classRoomRepository.findById(request.getClassRoomId()).orElseThrow(() -> new RuntimeException("Class not found"));
//        Session session = sessionRepository.findById(request.getSessionId()).orElseThrow(() -> new RuntimeException("Session not found"));
//        AdminTenant tenant = adminTenantRepository.findById(tenantId).orElseThrow(() -> new RuntimeException("Tenant not found"));
//
//        // Score validation
//        double ca = request.getCaScore();
//        double exam = request.getExamScore();
//        if (ca < 0 || ca > 30 || exam < 0 || exam > 70) {throw new IllegalArgumentException("CA must be 0-30 and Exam must be 0-70");}
//        // Duplicate check
//        boolean exists = studentResultRepository.existsByStudentAndSubjectAndClassRoomAndSessionAndTermAndAdminTenant(
//                student, subject, classRoom, session, request.getTerm(), tenant
//        );
//        if (exists) {throw new RuntimeException("Result already exists for this student in this term and subject.");}
//        // Save new result
//        StudentResult result = new StudentResult();
//        result.setStudent(student);
//        result.setSubject(subject);
//        result.setClassRoom(classRoom);
//        result.setSession(session);
//        result.setTerm(request.getTerm());
//        result.setCaScore(ca);
//        result.setExamScore(exam);
//        result.setTotalScore(ca + exam);
//        result.setAdminTenant(tenant);
//
//        StudentResult saved = studentResultRepository.save(result);
//
//        return mapToResponse(saved);
//    }
//
//    private SingleStudentResultResponse mapToResponse(StudentResult result) {
//        SingleStudentResultResponse res = new SingleStudentResultResponse();
//        res.setResultId(result.getId());
//        res.setStudentId(result.getStudent().getStudentId());
//        res.setSubjectId(result.getSubject().getId());
//        res.setClassRoomId(result.getClassRoom().getClassId());
//        res.setSessionId(result.getSession().getSessionId());
//        res.setTerm(result.getTerm());
//        res.setCaScore(result.getCaScore());
//        res.setExamScore(result.getExamScore());
//        res.setTotalScore(result.getTotalScore());
//        res.setCreatedAt(result.getCreatedAt());
//        return res;
//    }
//}
