package org.example.parentPackage.dto.parentRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.data.model.Title;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParentRegisterRequest {
    private Title title;
    private String firstName;
    private String lastName;
    private String email;
    private String phone; // Added phone number
    private List<String> studentCodes;
}