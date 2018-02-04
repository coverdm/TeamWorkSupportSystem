package com.matuszak.engineer.project.exceptions;

public class TaskNotFoundException extends  RuntimeException{

    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
