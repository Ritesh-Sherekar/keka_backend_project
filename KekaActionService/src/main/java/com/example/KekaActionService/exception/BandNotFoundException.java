package com.example.KekaActionService.exception;

public class BandNotFoundException extends RuntimeException{
    public BandNotFoundException(String message){
        super(message);
    }
}
