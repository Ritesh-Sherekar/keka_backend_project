package com.example.KekaActionService.dto.attendanceDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRegularizationRequestDto {
    private Long employeeID;
    private LocalDate attendanceDate;
}
