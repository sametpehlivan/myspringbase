package com.pehlivan.security.handlers;

import com.pehlivan.security.security.authenticaitons.JwtAuthentication;
import com.pehlivan.security.security.filters.JwtFilter;
import com.pehlivan.security.services.AuthService;
import com.pehlivan.security.services.JwtTokenService;
import com.pehlivan.security.services.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@AllArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final JwtTokenService tokenService;
    private final AuthService authService;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        var requestHeader  = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (requestHeader != null) {
            if (StringUtils.hasText(requestHeader) && requestHeader.startsWith(JwtFilter.AUTHENTICATION_SCHEME_JWT)) {
                String token = requestHeader.substring(JwtFilter.AUTHENTICATION_SCHEME_JWT.length()).trim();
                try {
                    if (tokenService.isValid(token)) {
                        UserDetails user = tokenService.getUserFromToken(token);
                        authService.logout(user.getUsername());
                    }
                }catch (Exception e){
                    // todo log invalid Token
                    return;
                }

            }
        }
    }
}
