package com.example.query_service.dto;

import com.example.query_service.enums.Half;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveDayDto {
    private LocalDate leaveDate;
    private Half half;
}
