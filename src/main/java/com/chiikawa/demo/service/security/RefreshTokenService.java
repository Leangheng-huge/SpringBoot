package com.chiikawa.demo.service.security;

import com.chiikawa.demo.entity.RefreshToken;
import com.chiikawa.demo.entity.User;
import com.chiikawa.demo.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(User user) {
        // TODO: generate as Base64 URL Encoder
        String refreshToken = UUID.randomUUID().toString();

        RefreshToken entity = new RefreshToken();
        entity.setToken(refreshToken);
        entity.setExpiresAt(LocalDateTime.now().plusHours(3));
        entity.setUser(user);

        return refreshTokenRepository.save(entity);
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
    }

    public RefreshToken verifyToken(RefreshToken token ) throws AuthenticationException {
        // expire and revoke token
        if (!token.isValid()) {
            refreshTokenRepository.delete(token);
            throw new AuthenticationException("Refresh token is expired or revoked");
        }
        return token;
    }

    public RefreshToken rotateRefreshToken(RefreshToken oldRefreshToken) {
        // Revoke the old token
        oldRefreshToken.setRevoked(true);
        refreshTokenRepository.save(oldRefreshToken);
        
        // Create a new token for the same user
        return createRefreshToken(oldRefreshToken.getUser());
    }
}
