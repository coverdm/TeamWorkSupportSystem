package com.matuszak.engineer.project.exceptions;

public class IssueRoomNotFoundException extends RuntimeException{

    public IssueRoomNotFoundException() {
    }

    public IssueRoomNotFoundException(String message) {
        super(message);
    }
}
