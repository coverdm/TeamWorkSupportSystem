package com.matuszak.engineer.model.exception;

public class ReceiverNotFoundException extends RuntimeException{
    public ReceiverNotFoundException() {
    }

    public ReceiverNotFoundException(String message) {
        super(message);
    }
}
