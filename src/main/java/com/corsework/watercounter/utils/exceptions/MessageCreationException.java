package com.corsework.watercounter.utils.exceptions;

public class MessageCreationException extends RuntimeException {

    public MessageCreationException(String msg, Throwable err) {
        super(msg, err);
    }

    public MessageCreationException(String msg) {
        super(msg);
    }
}
