package com.matuszak.engineer.auth.exceptions;

public class AuthorizationException extends RuntimeException{

    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
