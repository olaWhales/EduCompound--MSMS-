package org.example.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherListResponse {
    private String email;
    private String firstName;
    private String lastName;
//    private Long adminTenantId; // Optional: To confirm tenant context on the frontend
}