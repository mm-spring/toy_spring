package com.mm.bandit.security.config;

import com.mm.bandit.security.exception.JwtAccessDeniedHandler;
import com.mm.bandit.security.exception.JwtAuthenticationEntryPoint;
import com.mm.bandit.security.filter.JwtAuthenticationFilter;
import com.mm.bandit.security.filter.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@RequiredArgsConstructor
@Configurable
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
}
