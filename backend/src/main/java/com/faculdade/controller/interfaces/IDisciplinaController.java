package com.faculdade.controller.interfaces;

import com.faculdade.dto.request.DisciplinaRequest;
import com.faculdade.dto.response.DisciplinaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Disciplinas", description = "Endpoints para gerenciamento de disciplinas")
public interface IDisciplinaController {

    @Operation(summary = "Listar todas as disciplinas", description = "Retorna uma lista com todas as disciplinas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de disciplinas retornada com sucesso")
    ResponseEntity<List<DisciplinaResponse>> findAll();

    @Operation(summary = "Buscar disciplina por ID", description = "Retorna os dados de uma disciplina específica")
    @ApiResponse(responseCode = "200", description = "Disciplina encontrada")
    @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    ResponseEntity<DisciplinaResponse> findById(@PathVariable Integer id);

    @Operation(summary = "Listar disciplinas por curso", description = "Retorna todas as disciplinas de um curso específico")
    @ApiResponse(responseCode = "200", description = "Lista de disciplinas retornada com sucesso")
    ResponseEntity<List<DisciplinaResponse>> findByCursoId(@PathVariable Integer cursoId);

    @Operation(summary = "Criar nova disciplina", description = "Cria uma nova disciplina no sistema")
    @ApiResponse(responseCode = "201", description = "Disciplina criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou disciplina já existe")
    ResponseEntity<DisciplinaResponse> create(@RequestBody DisciplinaRequest request);

    @Operation(summary = "Atualizar disciplina", description = "Atualiza os dados de uma disciplina existente")
    @ApiResponse(responseCode = "200", description = "Disciplina atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    ResponseEntity<DisciplinaResponse> update(@PathVariable Integer id, @RequestBody DisciplinaRequest request);

    @Operation(summary = "Deletar disciplina", description = "Remove uma disciplina do sistema")
    @ApiResponse(responseCode = "204", description = "Disciplina deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    ResponseEntity<Void> delete(@PathVariable Integer id);
}


