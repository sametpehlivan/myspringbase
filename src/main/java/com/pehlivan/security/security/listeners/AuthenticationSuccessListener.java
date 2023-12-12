package com.pehlivan.security.security.listeners;

import com.pehlivan.security.security.adapters.SecurityUser;
import com.pehlivan.security.services.JpaUserDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationSuccessListener{
    private final JpaUserDetailService userService;
    @EventListener
    public void listen(AuthenticationSuccessEvent event){
        log.debug("User Logged In Okay");
        if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {

            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
            if(token.getPrincipal() instanceof SecurityUser user){
                userService.unlockUserAccount(user.getUsername());
                log.info("Login Success saved. USERNAME:" +  user.getUsername());
            }
        }


    }
}