package com.example.KekaActionService.dto.documentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSubmitRequestDto {
    private Long employeeID;
    private String typeOfDoc;
    private String subTypeDoc;
}
