package com.techchallenge.domain.auth.validator;

import com.techchallenge.application.dto.request.ClientCredentialsRequest;
import com.techchallenge.application.dto.request.LoginRequest;

public interface AuthenticateValidator {
    void validateUserCredentials(LoginRequest loginRequest);
    void validateClientCredentials(ClientCredentialsRequest clientCredentialsRequest);
}
