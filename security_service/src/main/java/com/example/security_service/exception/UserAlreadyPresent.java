package com.example.security_service.exception;

public class UserAlreadyPresent extends RuntimeException {
    public UserAlreadyPresent(String message) {
        super(message);
    }
}
