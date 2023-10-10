package com.pehlivan.security.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JwtTokenException extends BadCredentialsException {

    public JwtTokenException(String msg) {
        super(msg);
    }

    public JwtTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
