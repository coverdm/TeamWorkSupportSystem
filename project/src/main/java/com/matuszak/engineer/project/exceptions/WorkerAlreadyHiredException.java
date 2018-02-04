package com.matuszak.engineer.project.exceptions;

public class WorkerAlreadyHiredException extends RuntimeException{
    public WorkerAlreadyHiredException() {
    }

    public WorkerAlreadyHiredException(String message) {
        super(message);
    }
}
