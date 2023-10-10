package com.pehlivan.security.security.providers;


import com.pehlivan.security.security.adapters.SecurityUser;
import com.pehlivan.security.security.authenticaitons.JwtAuthentication;
import com.pehlivan.security.services.JwtTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class JwtTokenProviders  implements AuthenticationProvider {
    private JwtTokenService tokenService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication auth = (JwtAuthentication)authentication;
        String token =  auth.getToken();
        if (tokenService.isValid(token))
        {
            UserDetails user =  tokenService.getUserFromToken(token);
            return new JwtAuthentication(user.getUsername(),user.getAuthorities(),token);
        }
        throw new BadCredentialsException("Invalid Token!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
