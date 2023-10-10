package com.pehlivan.security.security.authenticaitons;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class JwtAuthentication implements Authentication {
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;
    private boolean authenticated;
    @Getter
    private String token;
    public JwtAuthentication(String token){
        this.username = null;
        this.authorities = null;
        this.token = token;
        this.authenticated = true;
    }
    public JwtAuthentication(String username,Collection<? extends GrantedAuthority> authorities,String token) {
        this.username = username;
        this.authorities = authorities;
        this.token = token;
        this.authenticated = true;

    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null; // JWT tabanlı kimlik doğrulama için şifre yok
    }

    @Override
    public Object getDetails() {
        return null; // Ekstra bilgi gerektiğinde buraya eklenebilir
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

}
