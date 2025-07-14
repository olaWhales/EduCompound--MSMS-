package org.example.services.teacherService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.data.model.Teacher;
import org.example.data.model.UserPrincipal;
import org.example.data.model.Users;
import org.example.data.repositories.TeacherRepository;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.teacherRequest.TeacherUpdateRequest;
import org.example.dto.responses.teacherResponse.TeacherUpdateResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.example.utilities.Utilities.AUTHENTICATION_NOT_FOUND_MESSAGE;
import static org.example.utilities.Utilities.AUTHENTICATION_REQUIRE_MESSAGE;

@Service
@AllArgsConstructor
@Slf4j
public class TeacherUpdateServiceImpl implements TeacherUpdateService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public TeacherUpdateResponse updateTeacher(String email, TeacherUpdateRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {throw new IllegalArgumentException(AUTHENTICATION_REQUIRE_MESSAGE);}
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Users currentUser = userPrincipal.users();
        if (userRepository.findByEmail(currentUser.getEmail()).isEmpty()) {throw new IllegalArgumentException(AUTHENTICATION_NOT_FOUND_MESSAGE);}
//        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long adminTenantId = currentUser.getAdminTenant().getTenantId();
        log.info("this is adminTenant {} " , adminTenantId);

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with email: " + email));
        log.info("this is user from repository {} " , user);

        Teacher teacher = teacherRepository.findByUsers(user).orElseThrow(() -> new IllegalArgumentException("Teacher record not found"));
        log.info("this is user role {} ", teacher);
        // Step 3: Ensure the teacher belongs to the same tenant as the admin
        if (!teacher.getAdminTenant().getTenantId().equals(adminTenantId)) {throw new SecurityException("Unauthorized: Teacher does not belong to your tenant");}
        // Step 4: Perform update
        user.setEmail(request.getEmail() != null ? request.getEmail() : user.getEmail());
        user.setFirstName(request.getFirstName() != null ? request.getFirstName() : user.getFirstName());
        user.setLastName(request.getLastName() != null ? request.getLastName() : user.getLastName());
        user.setCreatedAt(new Date());

        userRepository.save(user);

        return TeacherUpdateResponse.builder()
                .message("Teacher updated successfully")
                .updatedAt(new Date())
                .build();
    }
}