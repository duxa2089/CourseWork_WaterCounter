package com.corsework.watercounter.utils.exceptions;

public class TokenCreationException extends RuntimeException {

    public TokenCreationException(Exception e) {
        super(e);
    }

    public TokenCreationException(String e) {
        super(e);
    }
}
