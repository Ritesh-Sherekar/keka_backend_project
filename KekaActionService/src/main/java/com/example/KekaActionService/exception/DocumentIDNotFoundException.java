package com.example.KekaActionService.exception;

public class DocumentIDNotFoundException extends RuntimeException{
    public DocumentIDNotFoundException(String message){
        super(message);
    }
}
