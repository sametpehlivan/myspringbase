package com.pehlivan.security.security.filters;

import com.pehlivan.security.security.authenticaitons.JwtAuthentication;
import com.pehlivan.security.security.managers.JwtAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private JwtAuthenticationManager authenticationManager;
    public static final String AUTHENTICATION_SCHEME_JWT = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var requestHeader  = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (requestHeader != null){
            requestHeader = requestHeader.trim();
            if (StringUtils.startsWithIgnoreCase(requestHeader,AUTHENTICATION_SCHEME_JWT)){
                String token = requestHeader.replace("Bearer ","");
                var auth = authenticationManager.authenticate(new JwtAuthentication(token));
                if (auth.isAuthenticated()){
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            else if (requestHeader.equalsIgnoreCase(AUTHENTICATION_SCHEME_JWT)) {
                throw new BadCredentialsException("Empty jwt authentication token");
            }
            else throw new BadCredentialsException("Invalid jwt authentication token");
        }
        filterChain.doFilter(request,response);
    }
}
