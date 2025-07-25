package com.techchallenge.domain.auth.service;

import com.techchallenge.application.dto.request.ClientCredentialsRequest;
import com.techchallenge.application.dto.request.LoginRequest;
import com.techchallenge.application.dto.response.LoginResponse;
import com.techchallenge.application.mapper.AuthMapper;
import com.techchallenge.domain.auth.enums.TokenType;
import com.techchallenge.domain.auth.exception.AuthException;
import com.techchallenge.domain.auth.validator.AuthenticateValidator;
import com.techchallenge.domain.user.entity.User;
import com.techchallenge.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final TokenGenerator tokenGenerator;
    private final UserService userService;
    private final AuthMapper authMapper;
    private final AuthenticateValidator authenticateValidator;


    public AuthService(TokenGenerator tokenGenerator, UserService userService, AuthenticateValidator authenticateValidator, AuthMapper authMapper) {
        this.tokenGenerator = tokenGenerator;
        this.userService = userService;
        this.authMapper = authMapper;
        this.authenticateValidator = authenticateValidator;
    }

    public LoginResponse authenticate(LoginRequest loginRequest) {
        authenticateValidator.validateUserCredentials(loginRequest);
        User user = userService.findByUserName(loginRequest.username());
        String token = tokenGenerator.generateToken(user.getUserName(), TokenType.USER);
        return authMapper.toResponse(token);
    }

    public LoginResponse authenticateClient(ClientCredentialsRequest clientCredentialsRequest) {
        authenticateValidator.validateClientCredentials(clientCredentialsRequest);
        String token = tokenGenerator.generateToken(clientCredentialsRequest.clientId(), TokenType.CLIENT);
        return authMapper.toResponse(token);
    }
}
