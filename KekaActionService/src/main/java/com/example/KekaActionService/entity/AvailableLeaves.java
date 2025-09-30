package com.example.KekaActionService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AvailableLeaves {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long availableLeavesId;

    @OneToOne
    @JoinColumn(referencedColumnName = "employeeID")
    private Employee employee;

    private Float availablePaidLeaves;
    private Float availableSickLeaves;
    private Float availableCasualLeaves;
    private Float availableUnpaidLeaves;
    private Float availableParentalLeaves;

    private Float paidLeavesUsed;
    private Float sickLeavesUsed;
    private Float casualLeavesUsed;
    private Float unpaidLeavesUsed;
    private Float parentalLeavesUsed;
}
