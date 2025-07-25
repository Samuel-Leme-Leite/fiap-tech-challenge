package com.techchallenge.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ClientCredentialsRequest(
        @NotBlank
        @Schema(description = "Client ID", example = "your-client-id", requiredMode = Schema.RequiredMode.REQUIRED)
        String clientId,

        @NotBlank
        @Schema(description = "Client Secret", example = "your-client-secret", requiredMode = Schema.RequiredMode.REQUIRED)
        String clientSecret,

        @NotBlank
        @Schema(description = "Grant Type", example = "client_credentials", requiredMode = Schema.RequiredMode.REQUIRED)
        String grantType
) {
}
