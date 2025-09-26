package com.example.KekaActionService.dto.attendanceDto;

import com.example.KekaActionService.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceClockOutRequestDto {
    private Long id;
    private Long employeeID;
    private LocalDateTime checkOutTime;
}
