package org.example.studentBulkResultEntryPackage.dto.request.gradingConfigRequest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradingConfigRequest {
    @NotBlank(message = "Grade is required")
    private String gradeName;

    @NotNull(message = "Minimum score is required")
    @Min(0)
    @Max(100)
    private Integer minScore;

    @NotNull(message = "Maximum score is required")
    @Min(0)
    @Max(100)
    private Integer maxScore;

    private String remark;
}
