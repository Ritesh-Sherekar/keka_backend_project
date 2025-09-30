package com.example.query_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UsedLeaves {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long usedLeavesId;

    @OneToOne
    @JoinColumn(referencedColumnName = "employeeID")
    @JsonBackReference
    private Employee employee;

    private Float usedPaidLeaves;
    private Float usedSickLeaves;
    private Float usedCasualLeaves;
    private Float usedUnpaidLeaves;
    private Float usedParentalLeaves;

}
