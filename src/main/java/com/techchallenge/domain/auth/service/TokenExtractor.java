package com.techchallenge.domain.auth.service;

import java.util.Date;

public interface TokenExtractor {
    String extractUsername(String token);
    String extractTokenType(String token);
    Date extractExpiration(String token);
    String extractSubject(String token);
}
