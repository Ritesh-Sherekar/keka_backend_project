package com.example.KekaActionService.dto.shiftDto;

import com.example.KekaActionService.entity.Shift;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftResponseDto {
    private String message;
    private Shift shift;
}
