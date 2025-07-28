//package org.example.studentBulkResultEntryPackage.service;
//
//import lombok.RequiredArgsConstructor;
//import org.example.data.model.*;
//import org.example.data.repositories.*;
//import org.example.exceptions.ResultEntryException;
//import org.example.studentBulkResultEntryPackage.data.model.*;
//import org.example.studentBulkResultEntryPackage.data.repository.ContinuousAssessmentConfigRepository;
//import org.example.studentBulkResultEntryPackage.data.repository.ExamConfigRepository;
//import org.example.studentBulkResultEntryPackage.data.repository.GradingConfigRepository;
//import org.example.studentBulkResultEntryPackage.dto.request.AssessmentScoreDto;
//import org.example.studentBulkResultEntryPackage.dto.request.ExamScoreDto;
//import org.example.studentBulkResultEntryPackage.dto.request.StudentResultEntryRequest;
//import org.example.studentSingleResultEntryPackage.data.repository.StudentResultRepository;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class StudentResultEntryServiceImpl implements StudentResultEntryService {
//    private final StudentRepository studentRepository;
//    private final SubjectRepository subjectRepository;
//    private final ClassRoomRepository classRoomRepository;
//    private final SessionRepository sessionRepository;
//    private final AdminTenantRepository tenantRepository;
//    private final StudentResultRepository studentResultRepository;
//    private final ContinuousAssessmentConfigRepository assessmentConfigRepository;
//    private final ExamConfigRepository examConfigRepository;
//    private final GradingConfigRepository gradingConfigRepository;
//
//    @Override
//    public void enterStudentResult(StudentResultEntryRequest request) {
//        // Validate entities
//        Student student = studentRepository.findById(request.getStudentId())
//                .orElseThrow(() -> new ResultEntryException("Student not found with ID: " + request.getStudentId()));
//        Subject subject = subjectRepository.findById(request.getSubjectId())
//                .orElseThrow(() -> new ResultEntryException("Subject not found with ID: " + request.getSubjectId()));
//        ClassRoom classRoom = classRoomRepository.findById(request.getClassId())
//                .orElseThrow(() -> new ResultEntryException("Class not found with ID: " + request.getClassId()));
//        Session session = sessionRepository.findById(request.getSessionId())
//                .orElseThrow(() -> new ResultEntryException("Session not found with ID: " + request.getSessionId()));
//        AdminTenant tenant = student.getAdminTenant();
//
//        StudentResult result = new StudentResult();
//        result.setStudent(student);
//        result.setSubject(subject);
//        result.setClassRoom(classRoom);
//        result.setStatus("DRAFT");
//        result.setSession(session);
//        result.setTerm(request.getTerm());
//        result.setAdminTenant(tenant);
//        result.setCreatedAt(LocalDateTime.now());
//
//        // Process Continuous Assessments
//        List<AssessmentConfig> configList = assessmentConfigRepository.findByTenantIdAndIsActiveTrue(tenant.getId());
//        List<ContinuousAssessment> assessments = new ArrayList<>();
//        double caTotal = 0;
//
//        for (AssessmentConfig config : configList) {
//            Optional<AssessmentScoreDto> matchingInput = request.getAssessments().stream()
//                    .filter(a -> a.getAssessmentConfigId().equals(config.getId()))
//                    .findFirst();
//
//            if (config.getIsRequired() && matchingInput.isEmpty()) {
//                throw new ResultEntryException("Missing required assessment: " + config.getName());
//            }
//
//            if (matchingInput.isPresent()) {
//                ContinuousAssessment ca = new ContinuousAssessment();
//                ca.setAssessmentConfig(config);
//                ca.setScore(matchingInput.get().getScore());
//                ca.setStudentResult(result);
//                assessments.add(ca);
//                caTotal += (matchingInput.get().getScore() * config.getWeight()) / 100.0;
//            }
//        }
//
//        // Process Exam Assessments
//        List<ExamConfig> examConfigList = examConfigRepository.findByTenantIdAndIsActiveTrue(tenant.getId());
//        List<ExamAssessment> examAssessments = new ArrayList<>();
//        double examTotal = 0;
//
//        for (ExamConfig config : examConfigList) {
//            Optional<ExamScoreDto> matchingInput = request.getExamAssessments().stream()
//                    .filter(e -> e.getExamConfigId().equals(config.getId()))
//                    .findFirst();
//
//            if (config.getIsRequired() && matchingInput.isEmpty()) {
//                throw new ResultEntryException("Missing required exam: " + config.getName());
//            }
//
//            if (matchingInput.isPresent()) {
//                ExamAssessment ea = new ExamAssessment();
//                ea.setExamConfig(config);
//                ea.setScore(matchingInput.get().getScore());
//                ea.setStudentResult(result);
//                examAssessments.add(ea);
//                examTotal += (matchingInput.get().getScore() * config.getWeight()) / 100.0;
//            }
//        }
//
//        // Calculate Total Score
//        double total = caTotal + examTotal;
//        result.setTotalScore(total);
//
//        // Calculate Grade
//        List<GradingConfig> gradingConfigs = gradingConfigRepository.findByTenantIdAndIsActiveTrue(tenant.getId());
//        String grade = "N/A";
//        for (GradingConfig config : gradingConfigs) {
//            if (total >= config.getMinScore() && total <= config.getMaxScore()) {
//                grade = config.getGrade();
//                break;
//            }
//        }
//        result.setGrade(grade);
//
//        // Set Assessments and Save
//        result.setAssessments(assessments);
//        result.setExamAssessments(examAssessments);
//        studentResultRepository.save(result);
//    }
//}
