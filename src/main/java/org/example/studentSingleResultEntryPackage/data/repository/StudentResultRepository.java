//package org.example.studentSingleResultEntryPackage.data.repository;
//
//import org.example.data.model.*;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.UUID;
//@Repository
//public interface StudentResultRepository extends JpaRepository<StudentResult, UUID> {
//
//    boolean existsByStudentAndSubjectAndClassRoomAndSessionAndTermAndAdminTenant(
//            Student student,
//            Subject subject,
//            ClassRoom classRoom,
//            Session session,
//            String term,
//            AdminTenant adminTenant
//    );
////    Optional<StudentResult> findByStudentIdAndSubjectIdAndSessionIdAndTermAndClassRoomId(
////            UUID studentId, UUID subjectId, UUID sessionId, Term term, UUID classRoomId
////    );
////
////    List<StudentResult> findAllByClassRoomIdAndSessionIdAndTerm(
////            UUID classRoomId, UUID sessionId, Term term
////    );
////
////    List<StudentResult> findAllByStudentIdAndSessionIdAndTerm(
////            UUID studentId, UUID sessionId, Term term
////    );
//}
