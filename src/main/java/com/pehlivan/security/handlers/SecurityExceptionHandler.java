package com.pehlivan.security.handlers;

import com.pehlivan.security.dto.ExceptionResponse;
import com.pehlivan.security.security.exceptions.JwtTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ExceptionResponse handleBadCredentials(){
        return new ExceptionResponse("Kullanıcı adı veya parola hatalı");
    }
    @ExceptionHandler(LockedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ExceptionResponse handleLockedException(){
        return new ExceptionResponse("kullanıcı hesabı kilitli");
    }
    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ExceptionResponse handleDisableException(){
        return new ExceptionResponse("Kullanıcı banlandı");
    }
    @ExceptionHandler(JwtTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ExceptionResponse handleJwtTokenException(){
        return new ExceptionResponse("Geçersiz Token");
    }
    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ExceptionResponse handleInsufficientAuthenticationException(){
        return new ExceptionResponse("kullanıcı doğrulamak için yeterli bilgi yok");
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ExceptionResponse handleAccessDeniedException(){
        return  new ExceptionResponse("Yetkisiz kullanıcı");
    }

}
