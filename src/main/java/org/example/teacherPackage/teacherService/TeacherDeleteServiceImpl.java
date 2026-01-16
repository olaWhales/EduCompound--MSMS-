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

import static org.example.utilities.Utilities.*;

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

        Users user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(TEACHER_NOT_FOUND_WITH_EMAIL + email));
        Teacher teacher = teacherRepository.findByUsers(user).orElseThrow(() -> new IllegalArgumentException(TEACHER_RECORD_NOT_FOUND_FOR_EMAIL + email));
        if (!teacher.getAdminTenant().getTenantId().equals(adminTenantId)) {throw new SecurityException(UNAUTHORIZED_ACCESS_TEACHER_DOES_NOT_BELONG_TO_YOUR_TENANT);}

        teacherRepository.delete(teacher);
        userRepository.delete(user);

        return TeacherDeleteResponse.builder()
                .message(TEACHER_DELETED_SUCCESSFULLY)
                .build();
    }
}
