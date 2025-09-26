package com.example.KekaActionService.dto;

import com.example.KekaActionService.enums.LeaveType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveDto {
    private long employeeID;
    private long approverID;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double leaveDays;
    private String reason;
    private LeaveType leaveType;
}
