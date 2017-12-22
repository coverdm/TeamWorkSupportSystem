package com.matuszak.engineer.domain.project.exceptions;

public class InvalidHyperlinkException extends RuntimeException{

    public InvalidHyperlinkException(String message) {
        super(message);
    }
}
