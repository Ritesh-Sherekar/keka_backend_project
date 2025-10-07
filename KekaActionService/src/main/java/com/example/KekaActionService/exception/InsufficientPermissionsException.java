package com.example.KekaActionService.exception;

public class InsufficientPermissionsException extends RuntimeException {
    public InsufficientPermissionsException(String message) {
        super(message);
    }
}
