package org.example.service.teacherService;

import lombok.AllArgsConstructor;
import org.example.data.model.Admin;
import org.example.data.model.Teacher;
import org.example.data.model.Users;
import org.example.data.repositories.TeacherRepository;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.TeacherRegistrationRequest;
import org.example.service.TeacherRegistration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeacherRegistrationImp implements TeacherRegistration {
    private final UserRepository usersRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void completeTeacherRegistration(TeacherRegistrationRequest request, String token) {
        // Find user by email
        Optional<Users> userOpt = usersRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or token");
        }

        Users user = userOpt.get();
        // Update user details
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCreatedAt(new Date());
        usersRepository.save(user);

        // Create teacher record
        Teacher teacher = Teacher.builder()
                .admin(Admin.builder().tenantId(user.getAdmin().getTenantId()).build())
                .users(user)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        teacherRepository.save(teacher);
    }
}