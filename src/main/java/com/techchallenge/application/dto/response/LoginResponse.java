package com.techchallenge.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public record LoginResponse(
        @Schema(description = "Token JWT de acesso", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,

        @Schema(description = "Tipo do token", example = "Bearer")
        String tokenType,

        @Schema(description = "Tempo de expiração em segundos", example = "3600")
        int expiresIn,

        @Schema(description = "Nome do usuário logado", example = "joao.silva")
        String username
) {
}
