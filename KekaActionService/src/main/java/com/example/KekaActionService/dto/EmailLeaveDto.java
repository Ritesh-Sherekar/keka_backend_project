package com.example.KekaActionService.dto;

import com.example.KekaActionService.enums.LeaveStatus;
import com.example.KekaActionService.enums.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailLeaveDto {

    private long employeeID;
    private long approverID;
    private String employeeName;
    private String approverName;
    private LeaveType leaveType;
    private String reason;
    private LeaveStatus leaveStatus;
    private List<LeaveDayDto> leaveDays;
    private float leaveDaysCount;
    private String email;
}
