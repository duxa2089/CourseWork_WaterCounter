package com.corsework.watercounter.utils.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenBanValidateException extends RuntimeException {

    private final String type;

    public TokenBanValidateException(String msg, String type) {
         super(msg);
        this.type = type;
    }
}
