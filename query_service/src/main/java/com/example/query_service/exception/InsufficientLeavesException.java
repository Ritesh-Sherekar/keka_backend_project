package com.example.query_service.exception;

public class InsufficientLeavesException extends RuntimeException {
    public InsufficientLeavesException(String message) {
        super(message);
    }
}
