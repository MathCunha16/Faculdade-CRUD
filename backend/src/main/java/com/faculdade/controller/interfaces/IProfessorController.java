package com.faculdade.controller.interfaces;

import com.faculdade.dto.request.ProfessorRequest;
import com.faculdade.dto.request.UpdateProfessorRequest;
import com.faculdade.dto.response.ProfessorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Professores", description = "Endpoints para gerenciamento de professores")
public interface IProfessorController {

    @Operation(summary = "Listar todos os professores", description = "Retorna uma lista com todos os professores cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de professores retornada com sucesso")
    ResponseEntity<List<ProfessorResponse>> findAll();

    @Operation(summary = "Buscar professor por ID", description = "Retorna os dados de um professor específico")
    @ApiResponse(responseCode = "200", description = "Professor encontrado")
    @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    ResponseEntity<ProfessorResponse> findById(@PathVariable Integer id);

    @Operation(summary = "Criar novo professor", description = "Cria um novo professor no sistema")
    @ApiResponse(responseCode = "201", description = "Professor criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou professor já existe")
    ResponseEntity<ProfessorResponse> create(@RequestBody ProfessorRequest request);

    @Operation(summary = "Atualizar professor", description = "Atualiza os dados de um professor existente")
    @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    ResponseEntity<ProfessorResponse> update(@PathVariable Integer id, @RequestBody UpdateProfessorRequest request);

    @Operation(summary = "Deletar professor", description = "Remove um professor do sistema")
    @ApiResponse(responseCode = "204", description = "Professor deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    ResponseEntity<Void> delete(@PathVariable Integer id);
}

