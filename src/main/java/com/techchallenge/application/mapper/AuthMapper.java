package com.techchallenge.application.mapper;

import com.techchallenge.application.dto.response.LoginResponse;
import com.techchallenge.domain.auth.service.TokenExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    private  final TokenExtractor tokenExtractor;

    @Value("${jwt.expiration}")
    private int jwtExpiration;


    public AuthMapper(TokenExtractor tokenExtractor) {
        this.tokenExtractor = tokenExtractor;
    }

    public LoginResponse toResponse(String token) {
        return  new LoginResponse(
                token,
                "Bearer",
                jwtExpiration,
                tokenExtractor.extractSubject(token)
        );
    }
}
