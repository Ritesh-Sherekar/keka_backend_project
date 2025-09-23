package com.example.security_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userName;
    private String password;
    private Set<String> roles;
    private EmployeeDto employeeDto;
}
