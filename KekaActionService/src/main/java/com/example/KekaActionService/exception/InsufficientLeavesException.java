package com.example.KekaActionService.exception;

public class InsufficientLeavesException extends RuntimeException {
    public InsufficientLeavesException(String message) {
        super(message);
    }
}
