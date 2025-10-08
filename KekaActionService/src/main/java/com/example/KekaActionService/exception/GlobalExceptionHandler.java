package com.example.KekaActionService.exception;

import com.example.KekaActionService.errorResponse.ErrorResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Employee ID Not Found
    @ExceptionHandler(EmployeeIdNotFoundException.class)
    public ResponseEntity<ErrorResponses> employeeIDNotFound(EmployeeIdNotFoundException ex){
        ErrorResponses employeeIdNotFound = new ErrorResponses(ex.getMessage(), "Employee ID Not Found", LocalDateTime.now(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(employeeIdNotFound, HttpStatus.NOT_FOUND);
    }

    // Content Type Not Valid
    @ExceptionHandler(ContentTypeNotValidException.class)
    public ResponseEntity<ErrorResponses> contentTypeNotValid(ContentTypeNotValidException ex){
        ErrorResponses responses = new ErrorResponses(ex.getMessage(), "This content type not acceptable", LocalDateTime.now(), HttpStatus.NOT_ACCEPTABLE.toString());
        return new ResponseEntity<>(responses, HttpStatus.NOT_ACCEPTABLE);
    }

    // Document ID Not Found
    @ExceptionHandler(DocumentIDNotFoundException.class)
    public ResponseEntity<ErrorResponses> documentIDNotFound(DocumentIDNotFoundException ex){
        ErrorResponses responses = new ErrorResponses(ex.getMessage(), "Document Not Present", LocalDateTime.now(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(responses, HttpStatus.NOT_FOUND);
    }

    // Shift Not Present
    @ExceptionHandler(ShiftNotPresentException.class)
    public ResponseEntity<ErrorResponses> shiftNotPresent(ShiftNotPresentException ex){
        ErrorResponses responses = new ErrorResponses(ex.getMessage(), "Shift Not Present", LocalDateTime.now(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(responses, HttpStatus.NOT_FOUND);
    }

    // Shift Assign To Employee
    @ExceptionHandler(ShiftAssignToEmployeeException.class)
    public ResponseEntity<ErrorResponses> shiftAssignToEmployee(ShiftAssignToEmployeeException ex){
        ErrorResponses responses = new ErrorResponses(ex.getMessage(), "Shift assign to employee", LocalDateTime.now(), HttpStatus.CONFLICT.toString());
        return new ResponseEntity<>(responses, HttpStatus.CONFLICT);
    }

    // Department Not Found
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponses> departmentNotFound(DepartmentNotFoundException ex){
        ErrorResponses responses = new ErrorResponses(ex.getMessage(), "Department Not Found", LocalDateTime.now(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(responses, HttpStatus.NOT_FOUND);
    }

    // No Attendance Present On This Day
    @ExceptionHandler(NoAttendanceOnThisDayException.class)
    public ResponseEntity<ErrorResponses> noAttendanceOnThisDay(NoAttendanceOnThisDayException ex){
        ErrorResponses responses = new ErrorResponses(ex.getMessage(), "No Attendance Found On This Day", LocalDateTime.now(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(responses, HttpStatus.NOT_FOUND);
    }

    // Band Not Found Exception
    @ExceptionHandler(BandNotFoundException.class)
    public ResponseEntity<ErrorResponses> bandNotFound(BandNotFoundException ex){
        ErrorResponses responses = new ErrorResponses(ex.getMessage(), "Band Not Available", LocalDateTime.now(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(responses, HttpStatus.NOT_FOUND);
    }

    // Clock-In ID not Found
    @ExceptionHandler(ClockInIDNotFoundException.class)
    public ResponseEntity<ErrorResponses> clockInIDNotFound(ClockInIDNotFoundException ex){
        ErrorResponses responses = new ErrorResponses(ex.getMessage(), "Clock-In ID Not Found", LocalDateTime.now(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(responses, HttpStatus.NOT_FOUND);
    }

    // Already Clock-Out Exception
    @ExceptionHandler(AlreadyClockOutException.class)
    public ResponseEntity<ErrorResponses> alreadyClockOut(AlreadyClockOutException ex){
        ErrorResponses responses = new ErrorResponses(ex.getMessage(), "Already Clock Out", LocalDateTime.now(), HttpStatus.NOT_ACCEPTABLE.toString());
        return new ResponseEntity<>(responses, HttpStatus.NOT_ACCEPTABLE);
    }

}

