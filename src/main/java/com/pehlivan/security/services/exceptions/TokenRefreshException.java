package com.pehlivan.security.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException{
    private String token;
    public TokenRefreshException(String token,String message){
        super(message);
        this.token = token;
    }
    public TokenRefreshException(String message){
        super(message);
    }
}
