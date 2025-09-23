package com.example.KekaActionService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    @Id
   // @SequenceGenerator(name = "atte_seq",allocationSize = 50,initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID", nullable = false)
    private Employee employee;
    private LocalDate attendanceDate;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Boolean easyDelete = false;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PRESENT;

    public enum Status {
        PRESENT, ABSENT, LEAVE, HALF_DAY
    }
}
