package com.techchallenge.domain.auth.service;

public interface TokenValidator {
    boolean isTokenValid(String token);
    boolean isTokenExpired(String token);
    boolean isTokenType(String token, String expectedType);
}
