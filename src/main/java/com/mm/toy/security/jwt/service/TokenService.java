package com.mm.toy.security.jwt.service;

import com.mm.toy.security.jwt.dto.JwtToken;
import org.springframework.security.core.Authentication;

import java.util.Date;


public interface TokenService {

    JwtToken login(String email, String password);
    JwtToken issueTokens(String refreshToken);

    JwtToken generateToken(Authentication authentication);

    Authentication getAuthentication(String accessToken);

    boolean validateToken(String token);

    boolean logout(String refreshToken);

    boolean existsRefreshToken(String refreshToken);

    Date parseExpiration(String token);
}
