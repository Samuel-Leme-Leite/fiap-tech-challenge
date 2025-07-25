package com.techchallenge.domain.auth.service;

import com.techchallenge.domain.auth.enums.TokenType;

public interface TokenGenerator {
    String generateUserToken(String username);
    String generateClientToken();
    String generateToken(String subject, TokenType tokenType);
}
