package org.example.sessionPackage.dto.sessionRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.example.data.model.TermType;

import java.util.Date;

@Data
public class TermRequest {

    @NotBlank(message = "Session name is required")
    @Pattern(
            regexp = "^(\\d{4})(/\\d{4})?$",
            message = "Session must be in the format '2025' or '2025/2026'"
    )
    private String academicSession;

    @NotNull(message = "Term is required")
    private TermType term;

    @NotNull(message = "Start date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull(message = "End date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}