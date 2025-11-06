package com.faculdade.controller.interfaces;

import com.faculdade.dto.request.UsuarioRequest;
import com.faculdade.dto.response.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public interface IUsuarioController {

    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ResponseEntity<UsuarioResponse> findById(@PathVariable Integer id);

    @Operation(summary = "Buscar usuário por email", description = "Retorna os dados de um usuário pelo email")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ResponseEntity<UsuarioResponse> findByEmail(@PathVariable String email);

    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário já existe")
    ResponseEntity<UsuarioResponse> create(@RequestBody UsuarioRequest request);

    @Operation(summary = "Atualizar status do usuário", description = "Ativa ou desativa um usuário")
    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ResponseEntity<UsuarioResponse> atualizarStatus(@PathVariable Integer id, @RequestBody Boolean ativo);
}

