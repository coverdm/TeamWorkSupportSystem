package com.matuszak.engineer.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class IllegalJwtFormatException extends RuntimeException{

    public IllegalJwtFormatException() {
        super();
    }

    public IllegalJwtFormatException(String s) {
        super(s);
    }
}
