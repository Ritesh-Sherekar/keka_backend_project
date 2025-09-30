package com.example.KekaActionService.dto;

import com.example.KekaActionService.enums.Half;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveDayDto {
    private LocalDate leaveDate;
    private Half half;
}
