package com.pehlivan.security.security.filters;

import com.pehlivan.security.security.authenticaitons.JwtAuthentication;
import com.pehlivan.security.security.managers.JwtAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private JwtAuthenticationManager authenticationManager;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestHeader  = request.getHeader("Authorization");
        if (requestHeader != null && requestHeader.startsWith("Bearer")){
            String token = requestHeader.replace("Bearer ","");
            var auth = authenticationManager.authenticate(new JwtAuthentication(token));
            if (auth.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request,response);
    }
}
