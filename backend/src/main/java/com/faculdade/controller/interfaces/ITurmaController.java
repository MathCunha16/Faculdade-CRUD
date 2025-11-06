package com.faculdade.controller.interfaces;

import com.faculdade.dto.request.TurmaRequest;
import com.faculdade.dto.response.TurmaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Turmas", description = "Endpoints para gerenciamento de turmas")
public interface ITurmaController {

    @Operation(summary = "Listar todas as turmas", description = "Retorna uma lista com todas as turmas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de turmas retornada com sucesso")
    ResponseEntity<List<TurmaResponse>> findAll();

    @Operation(summary = "Buscar turma por ID", description = "Retorna os dados de uma turma específica")
    @ApiResponse(responseCode = "200", description = "Turma encontrada")
    @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    ResponseEntity<TurmaResponse> findById(@PathVariable Integer id);

    @Operation(summary = "Listar turmas por disciplina", description = "Retorna todas as turmas de uma disciplina específica")
    @ApiResponse(responseCode = "200", description = "Lista de turmas retornada com sucesso")
    ResponseEntity<List<TurmaResponse>> findByDisciplinaId(@PathVariable Integer disciplinaId);

    @Operation(summary = "Listar turmas por professor", description = "Retorna todas as turmas de um professor específico")
    @ApiResponse(responseCode = "200", description = "Lista de turmas retornada com sucesso")
    ResponseEntity<List<TurmaResponse>> findByProfessorId(@PathVariable Integer professorId);

    @Operation(summary = "Criar nova turma", description = "Cria uma nova turma no sistema")
    @ApiResponse(responseCode = "201", description = "Turma criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou turma já existe")
    ResponseEntity<TurmaResponse> create(@RequestBody TurmaRequest request);

    @Operation(summary = "Atualizar turma", description = "Atualiza os dados de uma turma existente")
    @ApiResponse(responseCode = "200", description = "Turma atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    ResponseEntity<TurmaResponse> update(@PathVariable Integer id, @RequestBody TurmaRequest request);

    @Operation(summary = "Deletar turma", description = "Remove uma turma do sistema")
    @ApiResponse(responseCode = "204", description = "Turma deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    ResponseEntity<Void> delete(@PathVariable Integer id);
}

