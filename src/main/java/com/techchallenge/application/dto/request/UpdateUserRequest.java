package com.techchallenge.application.dto.request;
import com.techchallenge.domain.shared.entity.Address;
import com.techchallenge.domain.shared.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record UpdateUserRequest(
        @Schema(description = "Nome do usuário", example = "João", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{user.firstName.notBlank}")
        String firstName,

        @Schema(description = "Sobrenome do usuário", example = "Silva", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{user.lastName.notBlank}")
        String lastName,

        @Schema(description = "Email do usuário", example = "exemplo@exemplo.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{user.email.notBlank}")
        @Email(message = "{user.email.invalid}")
        String email,

        @Schema(description = "Login do usuário", example = "joao.silva", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{user.userName.notBlank}")
        String userName,

        @Schema(description = "Endereço do usuário", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Valid
        Address address,

        @Schema(description = "Perfil do usuário", example = "CUSTOMER", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        UserRole profile
) {
}
