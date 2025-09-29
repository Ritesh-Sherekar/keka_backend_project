package com.example.query_service.exception;

import com.example.query_service.errorResponse.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> employeeIdNotFoundException(EmployeeIdNotFoundException employeeIdNotFoundException){
        ErrorResponse employeeIdNotFound = new ErrorResponse(employeeIdNotFoundException.getMessage(), "Employee Id Not Found", LocalDateTime.now(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(employeeIdNotFound, HttpStatus.NOT_FOUND);
    }

}
