package com.mm.bandit.security.filter;

import com.mm.bandit.presentation.payload.code.ErrorStatus;
import com.mm.bandit.security.exception.JwtAuthenticationException;
import com.mm.bandit.security.exception.CustomErrorSend;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException authException) {
            String errorCodeName = authException.getMessage();
            ErrorStatus errorStatus = ErrorStatus.valueOf(errorCodeName);

            CustomErrorSend.handleException(response, errorStatus, errorCodeName);
        }
    }
}
