package org.example.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private AdminTenant adminTenant;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Student> students;

}
