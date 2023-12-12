package com.pehlivan.security.services;

import com.pehlivan.security.security.exceptions.JwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtTokenService implements UserDetailsService {
    private final JpaUserDetailService userDetailService;

    private static final int EXPIRATION_MILLIS = 1000 * 60 * 10; // 10 minute
    private static final String SECRET_KEY = "myVeryVerySecretKey";

    public Date getTokenExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpired(String token) {
        return getTokenExpirationDate(token).before(new Date());
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailService.loadUserByUsername(username);
    }

    public boolean isValid(String token) throws JwtTokenException {
        try {
            var tokenExpired = !isTokenExpired(token);
            var tokenUsernameNotNull = getUsername(token) != null;
            return tokenExpired && tokenUsernameNotNull;
        }catch (Exception e){
            throw new JwtTokenException("INVALID TOKEN");
        }
    }

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public UserDetails getUserFromToken(String token) throws UsernameNotFoundException{
        String userName = getUsername(token);
        return loadUserByUsername(userName);
    }
    public String createToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


}
