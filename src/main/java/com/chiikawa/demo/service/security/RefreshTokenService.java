package com.chiikawa.demo.service.security;

import com.chiikawa.demo.entity.RefreshToken;
import com.chiikawa.demo.entity.User;
import com.chiikawa.demo.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
}
