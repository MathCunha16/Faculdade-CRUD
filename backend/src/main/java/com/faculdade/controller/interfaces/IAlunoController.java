package com.faculdade.controller.interfaces;

import com.faculdade.dto.request.AlunoRequest;
import com.faculdade.dto.request.UpdateAlunoRequest;
import com.faculdade.dto.response.AlunoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Alunos", description = "Endpoints para gerenciamento de alunos")
public interface IAlunoController {

    @Operation(summary = "Listar todos os alunos", description = "Retorna uma lista com todos os alunos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso")
    ResponseEntity<List<AlunoResponse>> findAll();

    @Operation(summary = "Buscar aluno por ID", description = "Retorna os dados de um aluno específico")
    @ApiResponse(responseCode = "200", description = "Aluno encontrado")
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    ResponseEntity<AlunoResponse> findById(@PathVariable Integer id);

    @Operation(summary = "Buscar aluno por matrícula", description = "Retorna os dados de um aluno pela matrícula")
    @ApiResponse(responseCode = "200", description = "Aluno encontrado")
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    ResponseEntity<AlunoResponse> findByMatricula(@PathVariable Integer matricula);

    @Operation(summary = "Criar novo aluno", description = "Cria um novo aluno no sistema")
    @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou aluno já existe")
    ResponseEntity<AlunoResponse> create(@RequestBody AlunoRequest request);

    @Operation(summary = "Atualizar aluno", description = "Atualiza os dados de um aluno existente")
    @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    ResponseEntity<AlunoResponse> update(@PathVariable Integer id, @RequestBody UpdateAlunoRequest request);

    @Operation(summary = "Deletar aluno", description = "Remove um aluno do sistema")
    @ApiResponse(responseCode = "204", description = "Aluno deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    ResponseEntity<Void> delete(@PathVariable Integer id);
}


