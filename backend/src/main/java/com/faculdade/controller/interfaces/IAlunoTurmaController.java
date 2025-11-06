package com.faculdade.controller.interfaces;

import com.faculdade.dto.request.AlunoTurmaRequest;
import com.faculdade.dto.request.UpdateAlunoTurmaRequest;
import com.faculdade.dto.response.AlunoTurmaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Matrículas", description = "Endpoints para gerenciamento de matrículas de alunos em turmas")
public interface IAlunoTurmaController {

    @Operation(summary = "Listar todas as matrículas", description = "Retorna uma lista com todas as matrículas")
    @ApiResponse(responseCode = "200", description = "Lista de matrículas retornada com sucesso")
    ResponseEntity<List<AlunoTurmaResponse>> findAll();

    @Operation(summary = "Buscar matrícula por ID", description = "Retorna os dados de uma matrícula específica")
    @ApiResponse(responseCode = "200", description = "Matrícula encontrada")
    @ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
    ResponseEntity<AlunoTurmaResponse> findById(@PathVariable Integer id);

    @Operation(summary = "Listar matrículas por aluno", description = "Retorna todas as matrículas de um aluno específico")
    @ApiResponse(responseCode = "200", description = "Lista de matrículas retornada com sucesso")
    ResponseEntity<List<AlunoTurmaResponse>> findByAlunoId(@PathVariable Integer alunoId);

    @Operation(summary = "Listar matrículas por turma", description = "Retorna todas as matrículas de uma turma específica")
    @ApiResponse(responseCode = "200", description = "Lista de matrículas retornada com sucesso")
    ResponseEntity<List<AlunoTurmaResponse>> findByTurmaId(@PathVariable Integer turmaId);

    @Operation(summary = "Matricular aluno em turma", description = "Realiza a matrícula de um aluno em uma turma")
    @ApiResponse(responseCode = "201", description = "Aluno matriculado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos, aluno já matriculado ou sem vagas")
    ResponseEntity<AlunoTurmaResponse> matricularAluno(@RequestBody AlunoTurmaRequest request);

    @Operation(summary = "Atualizar notas e frequência", description = "Atualiza as notas e frequência de um aluno em uma turma")
    @ApiResponse(responseCode = "200", description = "Notas atualizadas com sucesso")
    @ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
    ResponseEntity<AlunoTurmaResponse> atualizarNotas(@PathVariable Integer id, @RequestBody UpdateAlunoTurmaRequest request);

    @Operation(summary = "Cancelar matrícula", description = "Remove a matrícula de um aluno em uma turma")
    @ApiResponse(responseCode = "204", description = "Matrícula cancelada com sucesso")
    @ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
    ResponseEntity<Void> cancelarMatricula(@PathVariable Integer id);
}

