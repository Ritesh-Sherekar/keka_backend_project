package com.example.security_service.exception;

public class InvalidRolesException extends RuntimeException {
    public InvalidRolesException(String message) {
        super(message);
    }
}
