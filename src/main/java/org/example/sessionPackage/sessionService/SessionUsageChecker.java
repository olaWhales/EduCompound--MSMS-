package org.example.sessionPackage.sessionService;

import lombok.RequiredArgsConstructor;
import org.example.data.model.Session;
import org.example.data.repositories.StudentRepository;
import org.example.studentSingleResultEntryPackage.data.repository.ResultRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionUsageChecker {

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

    public boolean hasAcademicRecords(Session session) {
        return resultRepository.existsBySession(session);
    }
}
