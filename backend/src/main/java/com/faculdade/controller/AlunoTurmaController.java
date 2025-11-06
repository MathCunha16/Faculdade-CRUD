package com.faculdade.controller;

import com.faculdade.controller.interfaces.IAlunoTurmaController;
import com.faculdade.dto.request.AlunoTurmaRequest;
import com.faculdade.dto.request.UpdateAlunoTurmaRequest;
import com.faculdade.dto.response.AlunoTurmaResponse;
import com.faculdade.service.AlunoTurmaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
public class AlunoTurmaController implements IAlunoTurmaController {

    private final AlunoTurmaService alunoTurmaService;

    public AlunoTurmaController(AlunoTurmaService alunoTurmaService) {
        this.alunoTurmaService = alunoTurmaService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AlunoTurmaResponse>> findAll() {
        List<AlunoTurmaResponse> matriculas = alunoTurmaService.findAll();
        return ResponseEntity.ok(matriculas);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AlunoTurmaResponse> findById(@PathVariable Integer id) {
        AlunoTurmaResponse matricula = alunoTurmaService.findById(id);
        return ResponseEntity.ok(matricula);
    }

    @Override
    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<AlunoTurmaResponse>> findByAlunoId(@PathVariable Integer alunoId) {
        List<AlunoTurmaResponse> matriculas = alunoTurmaService.findByAlunoId(alunoId);
        return ResponseEntity.ok(matriculas);
    }

    @Override
    @GetMapping("/turma/{turmaId}")
    public ResponseEntity<List<AlunoTurmaResponse>> findByTurmaId(@PathVariable Integer turmaId) {
        List<AlunoTurmaResponse> matriculas = alunoTurmaService.findByTurmaId(turmaId);
        return ResponseEntity.ok(matriculas);
    }

    @Override
    @PostMapping
    public ResponseEntity<AlunoTurmaResponse> matricularAluno(@RequestBody AlunoTurmaRequest request) {
        AlunoTurmaResponse matricula = alunoTurmaService.matricularAluno(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(matricula);
    }

    @Override
    @PutMapping("/{id}/notas")
    public ResponseEntity<AlunoTurmaResponse> atualizarNotas(@PathVariable Integer id, @RequestBody UpdateAlunoTurmaRequest request) {
        AlunoTurmaResponse matricula = alunoTurmaService.atualizarNotas(id, request);
        return ResponseEntity.ok(matricula);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarMatricula(@PathVariable Integer id) {
        alunoTurmaService.cancelarMatricula(id);
        return ResponseEntity.noContent().build();
    }
}

