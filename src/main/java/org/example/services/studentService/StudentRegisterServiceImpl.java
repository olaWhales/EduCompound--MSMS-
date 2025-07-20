package org.example.services.studentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.*;
import org.example.data.model.UserPrincipal;
import org.example.data.repositories.*;
import org.example.dto.requests.StudentRegisterRequest;
import org.example.dto.responses.StudentRegisterResponse;
import org.example.utilities.StudentCodeGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class StudentRegisterServiceImpl implements StudentRegisterService {

    private final StudentRepository studentRepository;
    private final ClassRoomRepository classRepository;
    private final SessionRepository sessionRepository;
    private final SchoolBranchRepository branchRepository;
    private final ParentRepository parentRepository;
    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentCodeGenerator generateStudentCode ;

@Transactional
public StudentRegisterResponse studentRegistrationResponse(StudentRegisterRequest request) {
    // 👤 Get authenticated user and tenant
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
    Users adminUser = principal.users();
    AdminTenant tenant = adminUser.getAdminTenant();

    // 📘 Get current active session for tenant (instead of passing sessionYear/term from client)
    Session session = sessionRepository.findByAdminTenantAndIsActiveTrue(tenant)
            .orElseThrow(() -> new IllegalStateException("No active session for tenant"));

    // 🏫 Validate and fetch class
    ClassRoom classRoom = classRepository.findByAdminTenantAndSessionAndClassNameIgnoreCase(
            tenant, session, request.getClassName()
    ).orElseThrow(() -> new IllegalArgumentException("Class not found for the session and className"));

    // 🏢 Optional: Fetch and validate branch
    SchoolBranch branch = null;
    if (request.getBranchId() != null) {
        branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new IllegalArgumentException("Branch not found"));

        if (!branch.getAdminTenant().equals(tenant)) {
            throw new IllegalArgumentException("Branch does not belong to your school");
        }
    }

    // 👪 Optional: Fetch and validate parent
//    Parent parent = null;
//    if (request.getParentEmail() != null && !request.getParentEmail().isBlank()) {
//        Users parentUser = usersRepository.findByEmail(request.getParentEmail())
//                .orElseThrow(() -> new IllegalArgumentException("Parent user not found"));
//
//        if (!parentUser.getAdminTenant().equals(tenant)) {
//            throw new IllegalArgumentException("Parent user does not belong to your school");
//        }
//
//        parent = parentRepository.findByUsers(parentUser)
//                .orElseThrow(() -> new IllegalArgumentException("Parent record not found for the user"));
//    }

    // 🔒 Handle student login account creation
    Users studentUser = null;
    if (Boolean.TRUE.equals(request.getCreateLoginAccount())) {
        if (request.getStudentEmail() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Email and password are required to create student login account");
        }

        if (usersRepository.existsByEmail(request.getStudentEmail())) {
            throw new IllegalArgumentException("A user with this student email already exists");
        }

//        studentUser = Users.builder()
//                .email(request.getStudentEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.STUDENT)
//                .adminTenant(tenant)
//                .createdAt(new Date())
//                .build();
        String loginUsername = StudentCodeGenerator.generateUniqueStudentCode(
                classRoom.getClassName(), studentRepository, tenant
        );

        studentUser = Users.builder()
                .email(loginUsername) // ✅ studentCode is used for login
                .recoveryEmail(request.getStudentEmail()) // ✅ For password reset (you can add this to Users model)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .adminTenant(tenant)
                .createdAt(new Date())
                .build();

        usersRepository.save(studentUser);
    } else {
        request.setStudentEmail(null);
        request.setPassword(null);
    }

    // 🧾 Generate unique student code
    String studentCode = StudentCodeGenerator.generateUniqueStudentCode(
            classRoom.getClassName(), studentRepository, tenant
    );

    // 🧒 Create and save student
    Student student = Student.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .middleName(request.getMiddleName())
            .adminTenant(tenant)
            .classRoom(classRoom)
            .session(session)
            .studentCode(studentCode)
            .term(request.getTerm())
//            .schoolBranch(branch)
//            .parent(parent)
            .users(studentUser)
            .createdAt(new Date())
            .build();

    Student student1 = studentRepository.save(student);

        return StudentRegisterResponse.builder()
            .name(student1.getFirstName() + " " + student1.getLastName() + " " + student1.getMiddleName())
            .studentCode("Your student code number is => " + studentCode)
//            .studentId(student.getStudentId())
            .sessionYear(student1.getSession().getSessionYear())
            .term(session.getTerm())
            .message("Student registered successfully")
            .createdAt(student.getCreatedAt())
            .build();
    }
}
