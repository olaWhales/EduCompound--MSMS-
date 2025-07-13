package org.example.service.teacherService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.data.model.AdminTenant;
import org.example.data.model.Teacher;
import org.example.data.model.Users;
import org.example.data.repositories.TeacherRepository;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.TeacherDeleteRequest;
import org.example.dto.responses.TeacherDeleteResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class TeacherDeleteServiceImpl implements TeacherDeleteService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public TeacherDeleteResponse deleteTeacher(TeacherDeleteRequest request) {
        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long adminTenantId = currentUser.getAdminTenant().getTenantId();

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with email: " + request.getEmail()));
        Teacher teacher = teacherRepository.findByUsers(user)
                .orElseThrow(() -> new IllegalArgumentException("Teacher record not found for email: " + request.getEmail()));

        if (!teacher.getAdminTenant().getTenantId().equals(adminTenantId)) {
            throw new SecurityException("Unauthorized access: Teacher does not belong to your tenant");
        }

        teacherRepository.delete(teacher);

        return TeacherDeleteResponse.builder()
                .message("Teacher deleted successfully")
                .build();
    }
}