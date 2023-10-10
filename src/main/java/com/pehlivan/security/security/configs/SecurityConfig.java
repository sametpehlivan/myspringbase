package com.pehlivan.security.security.configs;

import com.pehlivan.security.security.filters.JwtFilter;
import com.pehlivan.security.security.providers.JwtTokenProviders;
import com.pehlivan.security.services.JpaUserDetailService;
import lombok.AllArgsConstructor;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

    private final static String[] basicAuthUrlPatterns = {
            "/login"
    };
    private final static String[] permittedUrlPatterns = {
            "/register",
            "/refresh_token"
    };

    @Bean
    @Order(1)
    public SecurityFilterChain basicFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider daoAuthenticationProvider
    ) throws Exception {
        return http
                .securityMatcher(basicAuthUrlPatterns)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests()
                    .requestMatchers(permittedUrlPatterns).permitAll()
                    .anyRequest().authenticated()
                    .and()
                .authenticationProvider(daoAuthenticationProvider)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .build();
    }
    @Bean
    public SecurityFilterChain jwtFilterChain(
            HttpSecurity http,
            JwtFilter jwtFilter,
            DaoAuthenticationProvider daoAuthenticationProvider
    ) throws Exception {
        return http
                .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                    .requestMatchers(permittedUrlPatterns).permitAll()
                    .anyRequest().authenticated()
                    .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .exceptionHandling()
                    .and()
                .build();
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
