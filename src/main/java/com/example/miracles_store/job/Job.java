package com.example.miracles_store.job;

import com.example.miracles_store.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Job {

    private final TokenRepository tokenRepository;

    @Scheduled(cron = "0 0 1 * * *")
    public void deleteLoggedOutTokens() {
        tokenRepository.deleteAll();
    }
}
