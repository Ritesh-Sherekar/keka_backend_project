package com.example.KekaActionService.exception;

public class NoAttendanceOnThisDayException extends RuntimeException{
    public NoAttendanceOnThisDayException(String message){
        super(message);
    }
}
