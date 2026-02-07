    package org.example.sessionPackage.dto.sessionResponse;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import lombok.Builder;
    import lombok.Data;
    import org.example.data.model.TermStatus;
    import org.example.data.model.TermType;

    import java.time.Instant;
    import java.time.LocalDate;
    import java.util.UUID;

    @Data
    @Builder
    public class TermResponse {
        private String schoolName;
        private String academicSession;
        private TermType term;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate startDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate endDate;

        private TermStatus status;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        private Instant createdAt;

        // Tenant info (scalars only)
        private UUID sessionId;
        private UUID tenantId;
        private String message;
    }