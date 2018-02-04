package com.matuszak.engineer.domain.project.exceptions;

public class IssueRoomNotFoundException extends RuntimeException{

    public IssueRoomNotFoundException() {
    }

    public IssueRoomNotFoundException(String message) {
        super(message);
    }
}
