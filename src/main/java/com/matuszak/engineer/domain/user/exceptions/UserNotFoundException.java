package com.matuszak.engineer.domain.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        this("No a such user");
    }
    public UserNotFoundException(String s) {
        super(s);
    }
}
