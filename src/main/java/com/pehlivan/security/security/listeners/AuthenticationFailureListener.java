package com.pehlivan.security.security.listeners;

import com.pehlivan.security.security.adapters.SecurityUser;
import com.pehlivan.security.services.JpaUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFailureListener {
    private final JpaUserDetailService userService;
    public void lockUserAccount(UserDetails userDetails){
        if (userDetails instanceof SecurityUser securityUser){
            userService.lockUserAccount(securityUser.getUsername());
        }
    }
    @EventListener
    public void listen(AuthenticationFailureBadCredentialsEvent event){
        if (event.getSource() instanceof UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
            if (token.getPrincipal() instanceof String username) {
                var user = userService.loadUserByUsername(username);
                lockUserAccount(user);
                log.debug("Attempted Username: " + token.getPrincipal());

            }
            if (token.getDetails() instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
                log.debug("Source IP: " + details.getRemoteAddress());
            }


        }
    }
}