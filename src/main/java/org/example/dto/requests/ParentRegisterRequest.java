package org.example.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParentRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String[] studentIds; // Optional: Link students now
}
