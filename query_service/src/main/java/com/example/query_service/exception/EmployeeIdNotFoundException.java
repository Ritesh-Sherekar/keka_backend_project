package com.example.query_service.exception;

public class EmployeeIdNotFoundException extends RuntimeException{
    public EmployeeIdNotFoundException(String message){
        super(message);
    }
}
