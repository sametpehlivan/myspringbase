package com.pehlivan.security.dto.auth;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationResponse {

    public String refreshToken;
    public String accessToken;
}
