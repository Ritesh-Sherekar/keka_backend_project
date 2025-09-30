package com.example.KekaActionService.entity;

import com.example.KekaActionService.enums.Badge;
import com.example.KekaActionService.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID", nullable = false,referencedColumnName = "employeeID")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler","shift"})
    private Employee employee;
    private LocalDate attendanceDate;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Boolean isDelete = false;
    private String grossHours;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Badge badge;

}
