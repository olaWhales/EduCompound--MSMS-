package org.example.studentSingleResultEntryPackage.data.repository;

import org.example.data.model.*;
import org.example.studentSingleResultEntryPackage.data.model.StudentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ResultRepository extends JpaRepository<StudentResult, UUID> {

//    boolean existsByStudentAndSubjectAndClassRoomAndSessionAndTermAndAdminTenant(
//            Student student,
//            Subject subject,
//            ClassRoom classRoom,
//            Term session,
//            String term,
//            AdminTenant adminTenant
//    );

//    boolean existsByStudentAndSubjectAndClassRoomAndTermAndAdminTenant(
//            Student student,
//            Subject subject,
//            ClassRoom classRoom,
//            Term term,
//            AdminTenant adminTenant
//    );
//
//
//    boolean existsBySession(Term term);
//    Optional<StudentResult> findByStudentIdAndSubjectIdAndSessionIdAndTermAndClassRoomId(
//            UUID studentId, UUID subjectId, UUID sessionId, Term term, UUID classRoomId
//    );
//
//    List<StudentResult> findAllByClassRoomIdAndSessionIdAndTerm(
//            UUID classRoomId, UUID sessionId, Term term
//    );
//
//    List<StudentResult> findAllByStudentIdAndSessionIdAndTerm(
//            UUID studentId, UUID sessionId, Term term
//    );

    // Check if a result already exists for this student, subject, class, term, and tenant
    boolean existsByStudentAndSubjectAndClassRoomAndTermAndAdminTenant(
            Student student,
            Subject subject,
            ClassRoom classRoom,
            Term term,
            AdminTenant adminTenant
    );

    // Optional helper
    boolean existsByTerm(Term term);

//    boolean existsBySession(Term term);
}
