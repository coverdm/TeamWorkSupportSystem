package com.matuszak.projects.task;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "no a such task", value = HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String s) {
        super(s);
    }
}
