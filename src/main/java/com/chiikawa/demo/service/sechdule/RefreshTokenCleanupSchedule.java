package com.chiikawa.demo.service.sechdule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenCleanupSchedule {
    @Scheduled(fixedRate = 5000)
    public void cleanupRefreshToken() throws InterruptedException{
        System.out.println("clean up refresh token..........");
        Thread.sleep(3000);
    }
}
