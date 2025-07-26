package com.techchallenge.application.controller;

import com.techchallenge.application.dto.request.CreateUserRequest;
import com.techchallenge.application.dto.request.UpdatePasswordRequest;
import com.techchallenge.application.dto.request.UpdateUserRequest;
import com.techchallenge.application.dto.response.ErrorResponse;
import com.techchallenge.application.dto.response.UserResponse;
import com.techchallenge.application.mapper.UserMapper;
import com.techchallenge.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Gerenciamento de usuários")
@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "409", description = "Usuário duplicado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário específico pelo seu ID")

    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userMapper.toResponse(userService.findById(id)));
    }

    @Operation(
            summary = "Criar um novo usuário",
            description = "Cria um novo usuário no sistema. O usuário é ativado por padrão ao ser criado.")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))
    @PostMapping("/")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        var user = userMapper.toEntity(createUserRequest);
        var createdUser = userService.createUser(user);
        return ResponseEntity.status(201).body(userMapper.toResponse(createdUser));
    }


    @Operation(
            summary = "Atualizar um usuário existente",
            description = "Atualiza os dados de um usuário existente. O ID do usuário deve ser fornecido na URL.")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        var updatedUser = userService.updateUser(id, updateUserRequest);
        return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    }

    @Operation(
            summary = "Deletar um usuário",
            description = "Desativa um usuário existente. O usuário não é removido do sistema, mas marcado como inativo.")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Ativar um usuário",
            description = "Ativa um usuário inativo. O usuário deve existir no sistema.")
    @ApiResponse(responseCode = "200", description = "Usuário ativado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))
    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserResponse> activateUser(@PathVariable("id") Long id) {
        userService.activateUser(id);
        return ResponseEntity.ok(userMapper.toResponse(userService.findById(id)));
    }

    @Operation(
            summary = "Alterar senha do usuário",
            description = "Altera a senha de um usuário existente. O usuário deve ser encontrado pelo ID.")
    @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso")
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable("id") Long id, @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest, Authentication authentication) {
        userService.changePassword(id, updatePasswordRequest, authentication.getName());
        return ResponseEntity.ok().build();
    }

}
