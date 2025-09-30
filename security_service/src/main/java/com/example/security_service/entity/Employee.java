package com.example.security_service.entity;

import com.example.security_service.entity.Department;
import com.example.security_service.entity.UsedLeaves;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "EMPLOYEE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    private Boolean isDeleted = false;

    @ManyToOne
    private Band band;

    @ManyToOne
    @JsonBackReference
    @ToString.Exclude
    private Department department;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference
    @ToString.Exclude
    private UsedLeaves usedLeaves;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // --- JPA Callbacks for timestamps ---
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (usedLeaves == null) {
            UsedLeaves uLeaves= new UsedLeaves();
            uLeaves.setUsedPaidLeaves(0f);
            uLeaves.setUsedSickLeaves(0f);
            uLeaves.setUsedCasualLeaves(0f);
            uLeaves.setUsedUnpaidLeaves(0f);
            uLeaves.setUsedParentalLeaves(0f);
            uLeaves.setEmployee(this);
            this.usedLeaves = uLeaves;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
