package com.matuszak.engineer.domain.project.exceptions;

public class TaskNotFoundException extends  RuntimeException{

    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
