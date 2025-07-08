package org.example.data.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String subject;
    private Float score;
    private String term;
    private Boolean approved;
}
