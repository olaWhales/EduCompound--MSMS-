package org.example.services.teacherService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.data.model.Role;
import org.example.data.model.UserPrincipal;
import org.example.data.model.Users;
import org.example.data.repositories.UserRepository;
import org.example.dto.responses.teacherResponse.TeacherListResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TeacherListServiceImpl implements TeacherListService {
    private final UserRepository userRepository;

    @Override
    public List<TeacherListResponse> getTeachersByTenant() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users currentUser;
        if (principal instanceof UserPrincipal) {
            currentUser = ((UserPrincipal) principal).users(); // Access the Users object
        } else if (principal instanceof String) {
            // Handle case where principal is a username (e.g., during anonymous access)
            throw new IllegalStateException("User principal is not authenticated properly");
        } else {
            throw new IllegalStateException("Unsupported principal type");
        }
        UUID adminTenantId = currentUser.getAdminTenant().getTenantId();
        return userRepository.findByRoleAndAdminTenant_TenantId(Role.TEACHER, adminTenantId)
                .stream()
                .map(user -> TeacherListResponse.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build())
                .collect(Collectors.toList());
    }
}