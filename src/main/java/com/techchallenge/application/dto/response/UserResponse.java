package com.techchallenge.application.dto.response;

import com.techchallenge.domain.shared.entity.Address;
import com.techchallenge.domain.shared.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UserResponse(
        @Schema(description = "ID do usuário", example = "1")
        Long id,

        @Schema(description = "Nome do usuário", example = "João")
        String firstName,

        @Schema(description = "Sobrenome do usuário", example = "Silva")
        String lastName,

        @Schema(description = "Email do usuário", example = "exemplo@exemplo.com")
        String email,

        @Schema(description = "Username do usuário", example = "joao.silva")
        String userName,

        @Schema(description = "Ativo ou Inativo", example = "true")
        boolean isActive,

        @Schema(description = "Data de criação do usuário", example = "2023-10-01T12:00:00Z")
        LocalDateTime createdAt,

        @Schema(description = "Data de atualização do usuário", example = "2023-10-01T12:00:00Z")
        LocalDateTime lastModifiedDate,

        @Schema(description = "Perfil do usuário", example = "CUSTOMER")
        UserRole profile,

        @Schema(description = "Endereço do usuário")
        Address address
) {
}
