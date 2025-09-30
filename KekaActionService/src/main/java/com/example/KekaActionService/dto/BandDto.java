package com.example.KekaActionService.dto;

import com.example.KekaActionService.enums.Bands;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BandDto {

    private Bands bands;
    private Float paidLeaves;
    private Float sickLeaves;
    private Float casualLeaves;
    private Float unpaidLeaves;
    private Float parentalLeaves;
}
