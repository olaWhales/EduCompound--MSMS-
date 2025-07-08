package org.example.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.data.model.Role;
import org.example.data.model.Admin;
import org.example.data.model.Users;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRegistrationRequest {
    private String email;

    private String password;

    private String firstName;
    private String lastName;

    private Users users;
    private Admin admin;
    private Role role;
}
