package com.example.KekaActionService.exception;

public class AlreadyClockOutException extends RuntimeException{
    public AlreadyClockOutException(String message){
        super(message);
    }
}
