package com.techchallenge.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techchallenge.application.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(
                "UNAUTHORIZED",
                "Token de autenticação necessário para acessar este recurso"
        );
        ErrorResponse errorResponse = new ErrorResponse(List.of(errorMessage));
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
