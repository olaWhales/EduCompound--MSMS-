package org.example.services.parentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.*;
import org.example.data.repositories.*;
import org.example.dto.requests.ParentRegisterRequest;
import org.example.dto.responses.ParentRegisterResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ParentRegisterServiceImpl implements ParentRegisterService {

    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ParentRegisterResponse registerParent(ParentRegisterRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        Users adminUser = principal.users();
        AdminTenant tenant = adminUser.getAdminTenant();

        // 🔐 Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("A user with this email already exists.");
        }

        // 👤 Create parent user
        Users parentUser = Users.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.PARENT)
                .adminTenant(tenant)
                .createdAt(new Date())
                .build();
        userRepository.save(parentUser);

        // 👪 Create Parent entity
        Parent parent = Parent.builder()
                .adminTenant(tenant)
                .users(parentUser)
                .build();

        // 🔗 Optional: Link students
        if (request.getStudentIds() != null && request.getStudentIds().length > 0) {
            List<Student> linkedStudents = new ArrayList<>();
            for (String studentIdStr : request.getStudentIds()) {
                UUID studentId = UUID.fromString(studentIdStr);
                Student student = studentRepository.findById(studentId)
                        .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));
                if (!student.getAdminTenant().equals(tenant)) {
                    throw new IllegalArgumentException("Student does not belong to your school.");
                }
                student.setParent(parent); // update reverse mapping
                linkedStudents.add(student);
            }
            parent.setStudents(linkedStudents);
        }

        parentRepository.save(parent);

        return ParentRegisterResponse.builder()
                .parentId(parent.getParentId())
                .message("Parent registered successfully")
                .createdAt(new Date())
                .build();
    }
}
