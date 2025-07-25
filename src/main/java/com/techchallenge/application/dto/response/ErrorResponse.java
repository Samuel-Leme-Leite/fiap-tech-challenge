package com.techchallenge.application.dto.response;

import java.util.List;

public record ErrorResponse(
        List<ErrorMessage> messages
) {
    public record ErrorMessage(
            String code,
            String message
    ){}
}
