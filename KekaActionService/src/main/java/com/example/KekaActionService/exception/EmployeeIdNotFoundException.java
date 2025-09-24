package com.example.KekaActionService.exception;

public class EmployeeIdNotFoundException extends RuntimeException{
    public EmployeeIdNotFoundException(String message){
        super(message);
    }
}
