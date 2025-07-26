package com.techchallenge.application.exception;

import com.techchallenge.application.dto.response.ErrorResponse;
import com.techchallenge.domain.auth.exception.AuthException;
import com.techchallenge.domain.user.exception.AccessDeniedException;
import com.techchallenge.domain.user.exception.InvalidPasswordException;
import com.techchallenge.domain.user.exception.UserAlreadyExistsException;
import com.techchallenge.domain.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(
                "USER_NOT_FOUND",
                ex.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(List.of(errorMessage));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(
                "USER_ALREADY_EXISTS",
                ex.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(List.of(errorMessage));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPassword(InvalidPasswordException ex) {
        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(
                "INVALID_PASSWORD",
                ex.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(List.of(errorMessage));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException ex) {
        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(
                "AUTH_ERROR",
                ex.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(List.of(errorMessage));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorResponse.ErrorMessage> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse.ErrorMessage(
                        "VALIDATION_ERROR",
                        error.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(
                "ACCESS_DENIED",
                ex.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(List.of(errorMessage));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(
                "INVALID_JSON",
                ex.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(List.of(errorMessage));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(
                "INVALID_PARAMETER",
                String.format("Invalid value '%s' for parameter '%s'", ex.getValue(), ex.getName())
        );
        ErrorResponse errorResponse = new ErrorResponse(List.of(errorMessage));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(
                "METHOD_NOT_ALLOWED",
                String.format("HTTP method '%s' is not supported for this endpoint", ex.getMethod())
        );
        ErrorResponse errorResponse = new ErrorResponse(List.of(errorMessage));
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex) {
        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(
                "RESOURCE_NOT_FOUND",
                "The requested resource was not found"
        );
        ErrorResponse errorResponse = new ErrorResponse(List.of(errorMessage));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Handler genérico para capturar qualquer exceção não tratada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(
                "INTERNAL_SERVER_ERROR",
                ex.getMessage()
        );
        ErrorResponse errorResponse = new ErrorResponse(List.of(errorMessage));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}