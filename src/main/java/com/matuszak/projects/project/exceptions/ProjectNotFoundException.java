package com.matuszak.projects.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No a such project")
public class ProjectNotFoundException extends RuntimeException{

    public ProjectNotFoundException() {
    }

    public ProjectNotFoundException(String s) {
        super(s);
    }

}
