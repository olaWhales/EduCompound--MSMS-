//package org.example.studentSingleResultEntryPackage.data.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.example.data.model.*;
//import org.hibernate.annotations.GenericGenerator;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class StudentResult {
//
//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
//    private UUID id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "student_id", nullable = false)
//    private Student student;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "subject_id", nullable = false)
//    private Subject subject;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "class_id", nullable = false)
//    private ClassRoom classRoom;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "session_id", nullable = false)
//    private Session session;
//
//    @Column(nullable = false)
//    private String term; // Example: "First Term", "Second Term", etc.
//
//    private Double caScore;    // e.g., over 30
//    private Double examScore;  // e.g., over 70
//    private Double totalScore;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "tenant_id", nullable = false)
//    private AdminTenant adminTenant;
//
//    private LocalDateTime createdAt;
//}
