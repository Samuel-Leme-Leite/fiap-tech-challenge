package com.techchallenge.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tech Challenge - Sistema de Gestão de Restaurantes")
                        .version("1.0")
                        .description("API para gerenciamento de usuários de restaurantes. " +
                                "Para testar endpoints protegidos: \n" +
                                "1) Use /auth/token ou /auth/login para obter um token \n" +
                                "2) Clique em 'Authorize' e cole o token \n" +
                                "3) Teste os endpoints normalmente"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Insira o token JWT obtido via /auth/login ou /auth/token")));
    }
}