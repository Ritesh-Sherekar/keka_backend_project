package com.example.security_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Long employeeID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String designation;
    private LocalDate joinDate;
    private Boolean active = true;
    private Boolean isDeleted = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
