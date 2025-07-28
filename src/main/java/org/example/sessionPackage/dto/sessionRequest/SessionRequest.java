package org.example.sessionPackage.dto.sessionRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class SessionRequest {

    @NotBlank(message = "Session name is required")
    @Pattern(
            regexp = "^(\\d{4})(/\\d{4})?$",
            message = "Session must be in the format '2025' or '2025/2026'"
    )
    private String sessionYear;

    @NotBlank(message = "Term is required")
    private String term;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private UUID branchId; // Optional
}