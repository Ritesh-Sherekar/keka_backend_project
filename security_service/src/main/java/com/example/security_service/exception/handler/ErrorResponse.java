package com.example.security_service.exception.handler;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private LocalDateTime timeStamp;
}
