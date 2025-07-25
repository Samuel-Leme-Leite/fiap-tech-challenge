package com.techchallenge.domain.auth.validator;

import com.techchallenge.application.dto.request.ClientCredentialsRequest;
import com.techchallenge.application.dto.request.LoginRequest;
import com.techchallenge.domain.auth.exception.AuthException;
import com.techchallenge.domain.user.entity.User;
import com.techchallenge.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AuthenticateValidatorImpl implements AuthenticateValidator{
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${auth.client.id}")
    private String validClientId;

    @Value("${auth.client.secret}")
    private String validClientSecret;

    public AuthenticateValidatorImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void validateUserCredentials(LoginRequest loginRequest) {
        User user = userService.findByUserName(loginRequest.username());
        if (user == null || !passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new AuthException("Usuário ou senha inválidos");
        }
    }

    @Override
    public void validateClientCredentials(ClientCredentialsRequest clientCredentialsRequest) {
        if (!validClientId.equals(clientCredentialsRequest.clientId()) || !validClientSecret.equals(clientCredentialsRequest.clientSecret())) {
            throw new AuthException("Credenciais inválidas");
        }
        if (!"client_credentials".equals(clientCredentialsRequest.grantType())) {
            throw new AuthException("Grant type inválido");
        }
    }
}
