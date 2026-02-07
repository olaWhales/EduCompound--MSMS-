//package org.example.studentPackage.studentService;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.example.Authentication.AuthenticatedUserProvider;
//import org.example.data.model.*;
//import org.example.data.repositories.*;
//import org.example.studentPackage.dto.studentRequest.StudentRegisterRequest;
//import org.example.studentPackage.dto.studentResponse.StudentRegisterResponse;
//import org.example.utilities.StudentCodeGenerator;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.util.Date;
//
//import static org.example.utilities.Utilities.*;
//
//@Service
//@RequiredArgsConstructor
//public class StudentRegisterServiceImpl implements StudentRegisterService {
//
//    private final StudentRepository studentRepository;
//    private final ClassRoomRepository classRepository;
//    private final TermRepository termRepository;
//    private final SchoolBranchRepository branchRepository;
//    private final UserRepository usersRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticatedUserProvider authenticatedUserProvider;
//
//    @Override
//    @Transactional
//    public StudentRegisterResponse studentRegistrationResponse(StudentRegisterRequest request) {
//
//        // 🔐 Authenticated admin + tenant
//        Users adminUser = authenticatedUserProvider.getCurrentUser();
//        AdminTenant tenant = adminUser.getAdminTenant();
//
//        // 📘 Active session
//        Term term = termRepository
//                .findByAdminTenantAndIsActiveTrue(tenant)
//                .orElseThrow(() -> new IllegalStateException(NO_ACTIVE_SESSION_FOR_TENANT));
//
//        // 🏫 Class validation
//        ClassRoom classRoom = classRepository
//                .findByAdminTenantAndSessionAndClassNameIgnoreCase(
//                        tenant, term, request.getClassName()
//                )
//                .orElseThrow(() ->
//                        new IllegalArgumentException(CLASS_NOT_FOUND_FOR_THE_SESSION_AND_CLASSNAME)
//                );
//
//        // 🚫 DUPLICATE CHECKS
//        if (Boolean.TRUE.equals(request.getCreateLoginAccount())) {
//
//            if (request.getStudentEmail() == null || request.getStudentEmail().isBlank()
//                    || request.getPassword() == null || request.getPassword().isBlank()) {
//
//                throw new IllegalArgumentException(
//                        "Student email and password are required to create a login account."
//                );
//            }
//
//            boolean emailExists = usersRepository
//                    .existsByRecoveryEmailAndAdminTenant(
//                            request.getStudentEmail().trim(),
//                            tenant
//                    );
//
//            if (emailExists) {
//                throw new IllegalArgumentException(
//                        "A student with this email already exists in this school."
//                );
//            }
//
//        } else {
//
//            boolean studentExists = studentRepository
//                    .existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndMiddleNameIgnoreCaseAndAdminTenantAndSessionAndClassRoom(
//                            request.getFirstName().trim(),
//                            request.getLastName().trim(),
//                            request.getMiddleName() == null ? "" : request.getMiddleName().trim(),
//                            tenant,
//                            term,
//                            classRoom
//                    );
//
//            if (studentExists) {
//                throw new IllegalArgumentException(
//                        "A student with the same name already exists in this class for the current session."
//                );
//            }
//        }
//
//        // 🧾 GENERATE STUDENT CODE ONCE (🔥 FIX)
//        String studentCode = StudentCodeGenerator.generateUniqueStudentCode(
//                classRoom.getClassName(),
//                studentRepository,
//                tenant
//        );
//
//        // 🔒 CREATE LOGIN ACCOUNT (OPTIONAL)
//        Users studentUser = null;
//
//        if (Boolean.TRUE.equals(request.getCreateLoginAccount())) {
//            studentUser = Users.builder()
//                    .email(studentCode)                          // ✅ SAME CODE
//                    .recoveryEmail(request.getStudentEmail())
//                    .password(passwordEncoder.encode(request.getPassword()))
//                    .role(Role.STUDENT)
//                    .adminTenant(tenant)
//                    .verified(true)
//                    .active(false)                               // admin controls activation
//                    .createdAt(new Date())
//                    .statusUpdatedAt(Instant.now())
//                    .build();
//
//            usersRepository.save(studentUser);
//        }
//
//        // 🧒 CREATE STUDENT
//        Student student = Student.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .middleName(request.getMiddleName())
//                .gender(request.getGender())
//                .adminTenant(tenant)
//                .classRoom(classRoom)
//                .term(term)
////                .term(term)
//                .studentCode(studentCode)                       // ✅ SAME CODE
//                .users(studentUser)
//                .createdAt(new Date())
//                .statusUpdatedAt(Instant.now())
//                .build();
//        Student savedStudent = studentRepository.save(student);
//
//        // ✅ RESPONSE
//        return StudentRegisterResponse.builder()
//                .name(
//                        savedStudent.getFirstName() + " " +
//                                savedStudent.getLastName() + " " +
//                                savedStudent.getMiddleName()
//                )
//                .studentCode(YOUR_STUDENT_CODE_NUMBER_IS + studentCode)
//                .sessionYear(savedStudent.getTerm().getAcademicSession().getSessionYear())
//                .term(savedStudent.getTerm().getTerm())
//                .message(STUDENT_REGISTERED_SUCCESSFULLY)
//                .createdAt(savedStudent.getCreatedAt())
//                .build();
//    }
//}

package org.example.studentPackage.studentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.Authentication.AuthenticatedUserProvider;
import org.example.data.model.*;
import org.example.data.repositories.*;
import org.example.studentPackage.dto.studentRequest.StudentRegisterRequest;
import org.example.studentPackage.dto.studentResponse.StudentRegisterResponse;
import org.example.utilities.StudentCodeGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.example.utilities.Utilities.*;

@Service
@RequiredArgsConstructor
public class StudentRegisterServiceImpl implements StudentRegisterService {

    private final StudentRepository studentRepository;
    private final ClassRoomRepository classRepository;
    private final TermRepository termRepository;
    private final SchoolBranchRepository branchRepository;
    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    @Transactional
    public StudentRegisterResponse studentRegistrationResponse(StudentRegisterRequest request) {

        // 🔐 Authenticated admin + tenant
        Users adminUser = authenticatedUserProvider.getCurrentUser();
        AdminTenant tenant = adminUser.getAdminTenant();

        // 📘 Fetch ACTIVE term (based on TermStatus.ACTIVE)
//        Optional<Term> term = termRepository
//                .findByAdminTenantAndStatus(tenant, TermStatus.ACTIVE);
        Term term = termRepository
                .findByAdminTenantAndStatus(tenant, TermStatus.ACTIVE)
                .orElseThrow(() ->
                        new IllegalStateException(NO_ACTIVE_SESSION_FOR_TENANT)
                );


        // 🏫 Validate class exists for this term
        ClassRoom classRoom = classRepository
                .findByAdminTenantAndTermAndClassNameIgnoreCase(
                        tenant, term, request.getClassName()
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(CLASS_NOT_FOUND_FOR_THE_SESSION_AND_CLASSNAME)
                );

        // 🚫 Check duplicate student (by name)
        boolean studentExists = studentRepository
                .existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndMiddleNameIgnoreCaseAndAdminTenantAndTermAndClassRoom(
                        request.getFirstName().trim(),
                        request.getLastName().trim(),
                        request.getMiddleName() == null ? "" : request.getMiddleName().trim(),
                        tenant,
                        term,
                        classRoom
                );

        if (studentExists) {
            throw new IllegalArgumentException(
                    "A student with the same name already exists in this class for the current session."
            );
        }

        // 🧾 Generate unique student code
        String studentCode = StudentCodeGenerator.generateUniqueStudentCode(
                classRoom.getClassName(),
                studentRepository,
                tenant
        );

        // 🔒 Optional login account creation
        Users studentUser = null;
        if (Boolean.TRUE.equals(request.getCreateLoginAccount())) {

            if (request.getStudentEmail() == null || request.getStudentEmail().isBlank()
                    || request.getPassword() == null || request.getPassword().isBlank()) {
                throw new IllegalArgumentException(
                        "Student email and password are required to create a login account."
                );
            }

            boolean emailExists = usersRepository
                    .existsByRecoveryEmailAndAdminTenant(
                            request.getStudentEmail().trim(),
                            tenant
                    );

            if (emailExists) {
                throw new IllegalArgumentException(
                        "A student with this email already exists in this school."
                );
            }

            studentUser = Users.builder()
                    .email(studentCode)  // use student code as login username
                    .recoveryEmail(request.getStudentEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.STUDENT)
                    .adminTenant(tenant)
                    .verified(true)
                    .active(false)       // admin activates manually
                    .createdAt(new Date())
                    .statusUpdatedAt(Instant.now())
                    .build();

            usersRepository.save(studentUser);
        }

        // 🧒 Create Student entity
        Student student = Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .gender(request.getGender())
                .adminTenant(tenant)
                .classRoom(classRoom)
                .term(term)                // ✅ only the Term entity
                .studentCode(studentCode)
                .users(studentUser)
                .createdAt(new Date())
                .statusUpdatedAt(Instant.now())
                .parent(null)              // optional, can be set later
                .build();

        Student savedStudent = studentRepository.save(student);

        // ✅ Build and return response
        return StudentRegisterResponse.builder()
                .name(savedStudent.getFirstName() + " " +
                        savedStudent.getLastName() + " " +
                        savedStudent.getMiddleName())
                .studentCode(YOUR_STUDENT_CODE_NUMBER_IS + studentCode)
                .sessionYear(savedStudent.getTerm().getAcademicSession().getSessionYear()) // from AcademicSessionEntity
                .term(savedStudent.getTerm().getTerm()) // TermType
                .message(STUDENT_REGISTERED_SUCCESSFULLY)
                .createdAt(savedStudent.getCreatedAt())
                .build();
    }
}
