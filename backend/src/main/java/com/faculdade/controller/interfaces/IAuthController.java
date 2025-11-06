package com.faculdade.controller.interfaces;

import com.faculdade.dto.request.LoginRequest;
import com.faculdade.dto.request.RegistroRequest;
import com.faculdade.dto.response.LoginResponse;
import com.faculdade.dto.response.RegistroResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Autenticação", description = "Endpoints para autenticação e registro de usuários")
public interface IAuthController {

    @Operation(summary = "Login", description = "Autentica um usuário no sistema e retorna um token")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

    @Operation(summary = "Registro de aluno", description = "Registra um novo usuário aluno no sistema usando a matrícula")
    @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou matrícula/email já em uso")
    @ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
    ResponseEntity<RegistroResponse> registrar(@RequestBody RegistroRequest request);
}

