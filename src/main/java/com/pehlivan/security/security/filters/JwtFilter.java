package com.pehlivan.security.security.filters;

import com.pehlivan.security.security.authenticaitons.JwtAuthentication;
import com.pehlivan.security.security.exceptions.JwtTokenException;
import com.pehlivan.security.services.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private JwtTokenService tokenService;
    public static final String AUTHENTICATION_SCHEME_JWT = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var requestHeader  = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (requestHeader != null) {
            if (StringUtils.hasText(requestHeader) && requestHeader.startsWith(AUTHENTICATION_SCHEME_JWT)) {
                String token = requestHeader.substring(AUTHENTICATION_SCHEME_JWT.length()).trim();
                if (tokenService.isValid(token)) {
                    UserDetails user = tokenService.getUserFromToken(token);
                    var auth = new JwtAuthentication(user.getUsername(), user.getAuthorities(), token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
