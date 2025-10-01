package com.example.KekaActionService.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shiftID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Shift shift;

    // --- JPA Callbacks for timestamps ---
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (usedLeaves == null) {
            UsedLeaves ul = new UsedLeaves();
            ul.setUsedPaidLeaves(0f);
            ul.setUsedSickLeaves(0f);
            ul.setUsedCasualLeaves(0f);
            ul.setUsedUnpaidLeaves(0f);
            ul.setUsedParentalLeaves(0f);
            ul.setEmployee(this);
            this.usedLeaves = ul;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
