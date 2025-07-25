package com.techchallenge.application.controller;

import com.techchallenge.application.dto.request.ClientCredentialsRequest;
import com.techchallenge.application.dto.request.LoginRequest;
import com.techchallenge.application.dto.response.LoginResponse;
import com.techchallenge.application.mapper.AuthMapper;
import com.techchallenge.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints de autenticação")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;

    }

    @PostMapping("/login")
    @Operation(summary = "User Authentication",
            description = "Endpoint para autenticação de usuários usando username e password")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @PostMapping("/token")
    @Operation(summary = "Client Credentials Authentication",
            description = "Endpoint para autenticação de aplicações usando Client Credentials flow")
    public ResponseEntity<LoginResponse> getClientToken(@Valid @RequestBody ClientCredentialsRequest request) {
        LoginResponse response = authService.authenticateClient(request);
        return ResponseEntity.ok(response);
    }
}
