package com.mm.bandit.security.jwt.service;

import com.mm.bandit.security.jwt.dto.JwtToken;
import org.springframework.security.core.Authentication;

import java.util.Date;

public interface TokenService {

    JwtToken login(String username, String password);
    JwtToken issueTokens(String refreshToken);

    JwtToken generateToken(Authentication authentication);

    Authentication getAuthentication(String accessToken);

    boolean validateToken(String token);

    boolean logout(String refreshToken);

    boolean existsRefreshToken(String refreshToken);

    Date parseExpiration(String token);
}
