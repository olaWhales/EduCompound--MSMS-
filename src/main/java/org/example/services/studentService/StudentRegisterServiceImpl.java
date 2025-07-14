package org.example.services.studentService;

import lombok.AllArgsConstructor;
import org.example.data.model.*;
import org.example.data.repositories.ClassRoomRepository;
import org.example.data.repositories.SessionRepository;
import org.example.data.repositories.StudentRepository;
import org.example.dto.requests.StudentRegisterRequest;
import org.example.dto.responses.StudentRegisterResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class StudentRegisterServiceImpl implements StudentRegisterService {
    private final SessionRepository sessionRepository;
    private final ClassRoomRepository classRepository;
    private final StudentRepository studentRepository;

    @Override
    public StudentRegisterResponse studentRegistrationResponse(StudentRegisterRequest studentRegisterRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        if (principal == null) {
            throw new IllegalArgumentException("User not authenticated");
        }
        Users currentUser = principal.users();
        AdminTenant tenanId = currentUser.getAdminTenant();
        // Validate class and session
        ClassRoom classEntity = classRepository.findById(studentRegisterRequest.getClassId())
                .orElseThrow(() -> new IllegalArgumentException("Class not found"));

        Session sessionEntity = sessionRepository.findById(studentRegisterRequest.getSessionId())
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        // Create student
        Student student = new Student();
        student.setAdminTenant(tenanId);
        student.setFirstName(studentRegisterRequest.getFirstName());
        student.setLastName(studentRegisterRequest.getLastName());
        student.setClassRoom(classEntity);
        student.setSession(sessionEntity);
        student.setCreatedAt(new Date());

        studentRepository.save(student);

        return StudentRegisterResponse.builder()
                .studentId(student.getStudentId())
                .message("Student registered successfully")
                .createdAt(student.getCreatedAt())
                .build();
    }
}