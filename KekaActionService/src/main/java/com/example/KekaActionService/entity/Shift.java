package com.example.KekaActionService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long shiftId;

    @Column(nullable = false, unique = true)
    private String shiftName;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalTime startTime;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalTime endTime;
}
