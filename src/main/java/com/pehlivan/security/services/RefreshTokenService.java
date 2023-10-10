package com.pehlivan.security.services;

import com.pehlivan.security.model.RefreshToken;
import com.pehlivan.security.model.User;
import com.pehlivan.security.repository.RefreshTokenRepository;
import com.pehlivan.security.services.exceptions.NotFoundException;
import com.pehlivan.security.services.exceptions.TokenRefreshException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
@AllArgsConstructor
public class RefreshTokenService {
    private static final int EXPIRATION_MILLIS = 60 * 15 * 1000;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JpaUserDetailService userDetailService;
    @Transactional
    public RefreshToken createToken(String username){
        var user = userDetailService.getByUserName(username);
        var token = createRefreshToken(user);
        updateUserToken(user,token);
        return refreshTokenRepository.save(token);
    }
    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(LocalDateTime.now().plus(EXPIRATION_MILLIS, ChronoUnit.MILLIS));

        return refreshToken;
    }
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(()-> new NotFoundException("Invalid Token!!"));
    }
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken().toString(), "Refresh token was expired. Please make a new login request");
        }
        return token;
    }
    @Transactional
    public void deleteByUsername(String  username) {
        var user = userDetailService.getByUserName(username);
        var token = user.getRefreshToken();
        user.setRefreshToken(null);
        token.setUser(null);
        refreshTokenRepository.save(token);
    }
    public void updateUserToken(User user,RefreshToken newToken){
        var oldToken = user.getRefreshToken();
        if (oldToken != null){

            oldToken.setUser(null);
        }
        user.setRefreshToken(newToken);
        newToken.setUser(user);
    }

}
