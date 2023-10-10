package com.pehlivan.security.controllers;

import com.pehlivan.security.dto.auth.AuthenticationResponse;
import com.pehlivan.security.dto.auth.RefreshTokenReqDto;
import com.pehlivan.security.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    @GetMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity
                .ok()
                .body(authService.create(username));
    }
    @GetMapping(value = "/refresh_token")
    public ResponseEntity<AuthenticationResponse>  refreshToken(
            @RequestBody RefreshTokenReqDto refreshToken
    ){
        return ResponseEntity
                .ok()
                .body(
                        authService.refreshToken(refreshToken.refreshToken)
                );
    }
    @GetMapping(value = "/logout")
    public ResponseEntity logout(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        authService.logout(username);
        return ResponseEntity.ok().build();
    }
}
