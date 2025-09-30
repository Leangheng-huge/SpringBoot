package com.chiikawa.demo.service.sechdule;

import com.chiikawa.demo.service.security.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenCleanupSchedule {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Scheduled(cron = "0 0 0/6 * * ?") // Run every 6 hours starting at midnight (00:00, 06:00, 12:00, 18:00)
     //`0/6` - hours (every 6 hours starting from 0, so 0, 6, 12, 18)

    public void cleanupRefreshToken() throws InterruptedException {
        refreshTokenService.deleteExpirationAndRevokedToken();
    }
}
