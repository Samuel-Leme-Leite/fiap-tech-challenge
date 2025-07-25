package com.techchallenge.infrastructure.security;

import com.techchallenge.domain.auth.service.TokenExtractor;
import com.techchallenge.domain.auth.service.TokenValidator;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenValidator implements TokenValidator {
    private final TokenExtractor tokenExtractor;

    public JwtTokenValidator(TokenExtractor tokenExtractor) {
        this.tokenExtractor = tokenExtractor;
    }


    @Override
    public boolean isTokenValid(String token) {
        return !isTokenExpired(token) && tokenExtractor.extractSubject(token) != null;
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = tokenExtractor.extractExpiration(token);
            return expiration != null && expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public boolean isTokenType(String token, String expectedType) {
        try {
            String tokenType = tokenExtractor.extractTokenType(token);
            return expectedType.equals(tokenType);
        } catch (Exception e) {
            return false;
        }
    }
}
