package com.corsework.watercounter.utils.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageFindException extends RuntimeException {

    private String error;

    public MessageFindException(String message, String error) {
        super(message);
        this.error = error;
    }
}
