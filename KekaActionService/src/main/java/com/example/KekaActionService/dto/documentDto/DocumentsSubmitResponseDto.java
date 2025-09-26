package com.example.KekaActionService.dto.documentDto;

import com.example.KekaActionService.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentsSubmitResponseDto {
    private String message;
    private Long employeeID;
    private Long docId;
}
