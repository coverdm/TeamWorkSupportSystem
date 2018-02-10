package com.matuszak.engineer.model.exception;

public class NotificationNotFoundException extends RuntimeException{
    public NotificationNotFoundException() {
    }

    public NotificationNotFoundException(String message) {
        super(message);
    }
}
