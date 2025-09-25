package com.example.KekaActionService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "EMPLOYEE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @SequenceGenerator(name = "emp_seq",allocationSize = 50,initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "emp_seq")
    private Long id;
    @Column(unique = true)
    private Long employeeID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String designation;
    private LocalDate joinDate;
    private Boolean active = true;
    private Boolean isDelete = false;

    @ManyToOne
    private Department department;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // --- JPA Callbacks for timestamps ---
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
