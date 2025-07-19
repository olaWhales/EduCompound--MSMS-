package org.example.data.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID paymentId;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private
    AdminTenant adminTenant;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "fee_id")
    private Fee fee;

    private Float amount;
    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String paystackRef;
}
