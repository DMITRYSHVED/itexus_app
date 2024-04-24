package com.example.miracles_store.config;

import com.example.miracles_store.entity.Token;
import com.example.miracles_store.repository.TokenRepository;
import com.example.miracles_store.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        String token = authHeader.substring(7);

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiration = jwtService.extractClaim(token, Claims::getExpiration)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        tokenRepository.save(new Token(token, Duration.between(currentTime, expiration).toSeconds()));
    }
}
