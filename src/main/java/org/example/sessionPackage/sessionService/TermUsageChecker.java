package org.example.sessionPackage.sessionService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.Term;
import org.example.data.repositories.StudentRepository;
import org.example.studentSingleResultEntryPackage.data.repository.ResultRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TermUsageChecker {

    private final StudentRepository studentRepository;
//    private final ResultRepository resultRepository;
//    private final AttendanceRepository attendanceRepository;
//
//    public boolean hasAcademicRecords(Session session) {
//        return studentRepository.existsBySession(session)
//                || resultRepository.existsBySession(session)
//                || attendanceRepository.existsBySession(session);
//    }

    private final ResultRepository resultRepository;

//    public boolean hasAcademicRecords(Term term) {
//        return resultRepository.existsBySession(term);
//    }

    public boolean hasAcademicRecords(Term term) {
        return resultRepository.existsByTerm(term);
    }

}
