package com.pehlivan.security.security.managers;

import com.pehlivan.security.security.providers.JwtTokenProviders;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtAuthenticationManager implements AuthenticationManager {
    private final JwtTokenProviders provider;
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (provider.supports(authentication.getClass())) {
            return provider.authenticate(authentication);
        }
        return authentication;
    }
}
