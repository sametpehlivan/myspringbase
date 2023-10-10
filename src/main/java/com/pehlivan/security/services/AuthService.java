package com.pehlivan.security.services;

import com.pehlivan.security.dto.auth.AuthenticationResponse;
import com.pehlivan.security.model.RefreshToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@AllArgsConstructor
public class AuthService {
    private final JwtTokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationResponse create(String username) {
        RefreshToken refreshToken = refreshTokenService.createToken(username);
        String accessToken = tokenService.createToken(username,new HashMap<>());
        return new AuthenticationResponse(refreshToken.getToken().toString(),accessToken);
    }

    public AuthenticationResponse refreshToken(String refreshToken) {
        var token = refreshTokenService.findByToken(refreshToken);
        token = refreshTokenService.verifyExpiration(token);
        return create(token.getUser().getUserName());
    }

    public void logout(String username) {
        refreshTokenService.deleteByUsername(username);
    }
}
