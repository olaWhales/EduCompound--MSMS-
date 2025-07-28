package org.example.data.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parent {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID parentId;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private AdminTenant adminTenant;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Title title;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Student> students = new ArrayList<>();
}
