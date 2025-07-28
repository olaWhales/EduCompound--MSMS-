package org.example.studentPackage.dto.studentResponse;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class StudentRegisterResponse {
    private String name ;
    private String studentCode; // 👈 Add this
//    private UUID studentId;
    private String sessionYear;
    private String term;
    private String message;
    private Date createdAt;


}
