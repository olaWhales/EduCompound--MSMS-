package org.example.dto.responses;

import lombok.Builder;
import lombok.Data;
import org.example.data.model.Session;

import java.util.Date;
import java.util.UUID;

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
