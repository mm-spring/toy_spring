package com.mm.bandit.presentation.payload.exception;

import com.mm.bandit.presentation.payload.code.ErrorStatus;

import javax.naming.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(ErrorStatus errorStatus) {
        super(errorStatus.name());
    }
}
