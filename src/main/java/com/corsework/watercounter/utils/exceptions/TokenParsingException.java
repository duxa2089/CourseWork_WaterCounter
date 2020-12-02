package com.corsework.watercounter.utils.exceptions;

public class TokenParsingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TokenParsingException(Exception e) {
        super(e);
    }

    public TokenParsingException(String e) {
        super(e);
    }
}
