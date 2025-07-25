package com.techchallenge.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public record UpdatePasswordRequest(
        @Schema(description = "Senha antiga do usuário", example = "SenhaAntiga123")
        @NotBlank
        String oldPassword,

        @Schema(description = "Nova senha do usuário", example = "NovaSenha123")
        @NotBlank
        String newPassword
) {
}
