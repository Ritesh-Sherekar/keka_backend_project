package com.example.KekaActionService.errorResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponses {
    private String message;
    private String details;
    private LocalDateTime localDateTime;
    private String status;
}
