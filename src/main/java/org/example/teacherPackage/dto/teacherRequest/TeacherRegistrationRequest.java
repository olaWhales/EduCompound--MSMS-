package org.example.teacherPackage.dto.teacherRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.data.model.Role;
import org.example.data.model.AdminTenant;
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
    private AdminTenant adminTenant;
    private Role role;
    private String phoneNumber ;
}
