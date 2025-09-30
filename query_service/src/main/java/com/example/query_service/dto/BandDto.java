package com.example.query_service.dto;

import com.example.query_service.enums.Bands;
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
