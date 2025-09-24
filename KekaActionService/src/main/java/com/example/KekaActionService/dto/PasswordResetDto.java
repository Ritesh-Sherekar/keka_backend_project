package com.example.KekaActionService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetDto {

    private String email;
    private String userName;
    private String resetToken;
}
