package org.example.data.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Admin admin;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private String firstName;
    private String lastName;
}
