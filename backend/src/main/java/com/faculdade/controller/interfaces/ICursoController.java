package com.faculdade.controller.interfaces;

import com.faculdade.dto.request.CursoRequest;
import com.faculdade.dto.response.CursoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Cursos", description = "Endpoints para gerenciamento de cursos")
public interface ICursoController {

    @Operation(summary = "Listar todos os cursos", description = "Retorna uma lista com todos os cursos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de cursos retornada com sucesso")
    ResponseEntity<List<CursoResponse>> findAll();

    @Operation(summary = "Buscar curso por ID", description = "Retorna os dados de um curso específico")
    @ApiResponse(responseCode = "200", description = "Curso encontrado")
    @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    ResponseEntity<CursoResponse> findById(@PathVariable Integer id);

    @Operation(summary = "Criar novo curso", description = "Cria um novo curso no sistema")
    @ApiResponse(responseCode = "201", description = "Curso criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou curso já existe")
    ResponseEntity<CursoResponse> create(@RequestBody CursoRequest request);

    @Operation(summary = "Atualizar curso", description = "Atualiza os dados de um curso existente")
    @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    ResponseEntity<CursoResponse> update(@PathVariable Integer id, @RequestBody CursoRequest request);

    @Operation(summary = "Deletar curso", description = "Remove um curso do sistema")
    @ApiResponse(responseCode = "204", description = "Curso deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    ResponseEntity<Void> delete(@PathVariable Integer id);
}


