package com.matuszak.engineer.auth.exceptions;

public class TokenHadBeenExpiredException extends RuntimeException {

    public TokenHadBeenExpiredException() {
        super();
    }

    public TokenHadBeenExpiredException(String message) {
        super(message);
    }
}
