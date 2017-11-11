package com.matuszak.engineer.domain.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class PasswordNotMatchedException extends RuntimeException{

    public PasswordNotMatchedException() {
        super();
    }

    public PasswordNotMatchedException(String s) {
        super(s);
    }
}
