package com.pehlivan.security.security.configs;

import com.pehlivan.security.security.filters.JwtFilter;
import com.pehlivan.security.services.JpaUserDetailService;
import lombok.AllArgsConstructor;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

    private  final LogoutHandler logoutHandler;
    
    private final static String[] permittedUrlPatterns = {
            "/register",
            "/refresh_token",
            "/error**",
            "/test2"
    };

    @Bean
    @Order(1)
    public SecurityFilterChain basicFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider daoAuthenticationProvider,
            JwtFilter jwtFilter,
            @Qualifier("delegatedAuthenticationEntryPoint")
            AuthenticationEntryPoint authEntryPoint
    ) throws Exception {
         http
             .csrf(AbstractHttpConfigurer::disable)
             .authorizeHttpRequests(ac -> {
                 ac.requestMatchers(permittedUrlPatterns).permitAll()
                         .anyRequest().authenticated();
             })
             .addFilterAfter(jwtFilter, BasicAuthenticationFilter.class)
             .httpBasic(basic -> {
                 basic.authenticationEntryPoint(authEntryPoint);
             })
             .logout(logout -> {
                logout
                    .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
             })
             .authenticationProvider(daoAuthenticationProvider)
             .sessionManagement(s -> {
                s.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
             });
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(JpaUserDetailService userDetailsService, PasswordEncoder passwordEncoder){
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

}
