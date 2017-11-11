package com.matuszak.engineer.domain.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class InvalidJwtFormatException extends RuntimeException{

    public InvalidJwtFormatException() {
        super();
    }

    public InvalidJwtFormatException(String s) {
        super(s);
    }
}
