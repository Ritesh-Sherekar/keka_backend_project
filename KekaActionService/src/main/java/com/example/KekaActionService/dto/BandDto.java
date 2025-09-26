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
    private int leaves;
}
