package org.example.teacherPackage.teacherService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.data.model.Teacher;
import org.example.data.model.UserPrincipal;
import org.example.data.model.Users;
import org.example.data.repositories.TeacherRepository;
import org.example.data.repositories.UserRepository;
import org.example.teacherPackage.dto.teacherResponse.TeacherDeleteResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class TeacherDeleteServiceImpl implements TeacherDeleteService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public TeacherDeleteResponse deleteTeacher(String email) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users currentUser = userPrincipal.users();
        UUID adminTenantId = currentUser.getAdminTenant().getTenantId();

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with email: " + email));
        Teacher teacher = teacherRepository.findByUsers(user)
                .orElseThrow(() -> new IllegalArgumentException("Teacher record not found for email: " + email));

        if (!teacher.getAdminTenant().getTenantId().equals(adminTenantId)) {
            throw new SecurityException("Unauthorized access: Teacher does not belong to your tenant");
        }

        teacherRepository.delete(teacher);
        userRepository.delete(user);

        return TeacherDeleteResponse.builder()
                .message("Teacher deleted successfully")
                .build();
    }
}
