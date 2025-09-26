package com.example.KekaActionService.entity;

import com.example.KekaActionService.enums.LeaveStatus;
import com.example.KekaActionService.enums.LeaveType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LEAVE")
public class Leave {

    @Id
    @SequenceGenerator(name = "leave_seq", allocationSize = 50, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leave_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID", nullable = false, referencedColumnName = "employeeID")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approverID", referencedColumnName = "employeeID")
    private Employee approver;

    private LocalDate startDate;
    private LocalDate endDate;
    private Double leaveDays;
    private LocalDateTime appliedAt;
    private String reason;
    private Boolean isDelete = false;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status = LeaveStatus.PENDING;

    @PrePersist
    protected void onCreate() {
        this.appliedAt = LocalDateTime.now();
    }
}
