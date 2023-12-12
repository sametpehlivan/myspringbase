package com.pehlivan.security.controllers;

import com.pehlivan.security.dto.auth.AuthenticationResponse;
import com.pehlivan.security.dto.auth.RefreshTokenReqDto;
import com.pehlivan.security.dto.response.Response;
import com.pehlivan.security.dto.response.ResponseData;
import com.pehlivan.security.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    @GetMapping(value = "/login")
    public ResponseEntity<ResponseData<AuthenticationResponse>> login(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var response = ResponseData.<AuthenticationResponse>responseDataBuilder().message("").data(authService.create(username)).build();
        return ResponseEntity.ok().body(response);
    }
    @PostMapping(value = "/refresh_token")
    public ResponseEntity<ResponseData<AuthenticationResponse>>  refreshToken(
            @RequestBody RefreshTokenReqDto refreshToken
    ){
        var response = ResponseData.<AuthenticationResponse>responseDataBuilder().message("").data(authService.refreshToken(refreshToken.refreshToken)).build();
        return ResponseEntity.ok().body(response);
    }
}
