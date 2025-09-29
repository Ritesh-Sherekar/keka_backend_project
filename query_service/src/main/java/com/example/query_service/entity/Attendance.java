package com.example.query_service.entity;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", nullable = false,referencedColumnName = "employeeId")
    private Employee employee;
    private LocalDate attendanceDate;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Boolean isDelete = false;
    private String grossHour;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PRESENT;

    public enum Status {
        PRESENT, ABSENT, LEAVE, HALF_DAY
    }

    @Enumerated(EnumType.STRING)
    private Badge badge = Badge.OUT;

    public enum Badge {
        IN, OUT, Regularized
    }

}
