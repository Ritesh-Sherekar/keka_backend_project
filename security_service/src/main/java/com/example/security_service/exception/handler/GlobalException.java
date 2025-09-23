package com.example.security_service.exception.handler;

import com.example.security_service.exception.InvalidCredentialsException;
import com.example.security_service.exception.InvalidRolesException;
import com.example.security_service.exception.UserAlreadyPresent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(InvalidRolesException.class)
    public ResponseEntity<ErrorResponse> invalidExceptionHandler(InvalidRolesException invalidRolesException){

        String message = invalidRolesException.getMessage();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("InvalidRole");
        errorResponse.setMessage(message);
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        log.error("InvalidRoleException : {}", errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyPresent.class)
    public ResponseEntity<ErrorResponse> userAlreadyPresent(UserAlreadyPresent userAlreadyPresent){

        String message = userAlreadyPresent.getMessage();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Registered User");
        errorResponse.setMessage(message);
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.CONFLICT.value());

        log.error("UserAlreadyPresentException : {}", errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> invalidCredentialsException(InvalidCredentialsException invalidCredentialsException){

        String message = invalidCredentialsException.getMessage();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Invalid Credentials");
        errorResponse.setMessage(message);
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        log.error("InvalidCredentialsException : {}", errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
