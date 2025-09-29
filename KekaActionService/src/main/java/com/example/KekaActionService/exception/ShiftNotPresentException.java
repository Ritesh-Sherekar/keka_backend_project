package com.example.KekaActionService.exception;

public class ShiftNotPresentException extends RuntimeException{
    public ShiftNotPresentException(String message){
        super(message);
    }
}
