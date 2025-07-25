package com.techchallenge.infrastructure.security;

import com.techchallenge.domain.auth.enums.TokenType;
import com.techchallenge.domain.auth.service.TokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenGenerator implements TokenGenerator {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    @Value("${jwt.client.expiration}")
    private int jwtClientExpiration;

    @Value("${jwt.refresh.expiration}")
    private int jwtRefreshExpiration;

    @Override
    public String generateUserToken(String username) {
        return generateToken(username, TokenType.USER);
    }

    @Override
    public String generateClientToken() {
        return generateToken("client", TokenType.CLIENT);
    }

    @Override
    public String generateToken(String subject, TokenType tokenType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", tokenType.name());
        int expiration = tokenType == TokenType.CLIENT ? jwtClientExpiration : jwtExpiration;
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .claims()
                .add(claims)
                .and()
                .subject(subject)
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(key)
                .compact();
    }
}
