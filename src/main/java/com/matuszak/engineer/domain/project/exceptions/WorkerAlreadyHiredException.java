package com.matuszak.engineer.domain.project.exceptions;

public class WorkerAlreadyHiredException extends RuntimeException{
    public WorkerAlreadyHiredException() {
    }

    public WorkerAlreadyHiredException(String message) {
        super(message);
    }
}
