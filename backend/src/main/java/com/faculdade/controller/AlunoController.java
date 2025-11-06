package com.faculdade.controller;

import com.faculdade.controller.interfaces.IAlunoController;
import com.faculdade.dto.request.AlunoRequest;
import com.faculdade.dto.request.UpdateAlunoRequest;
import com.faculdade.dto.response.AlunoResponse;
import com.faculdade.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController implements IAlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AlunoResponse>> findAll() {
        List<AlunoResponse> alunos = alunoService.findAll();
        return ResponseEntity.ok(alunos);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> findById(@PathVariable Integer id) {
        AlunoResponse aluno = alunoService.findById(id);
        return ResponseEntity.ok(aluno);
    }

    @Override
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<AlunoResponse> findByMatricula(@PathVariable Integer matricula) {
        AlunoResponse aluno = alunoService.findByMatricula(matricula);
        return ResponseEntity.ok(aluno);
    }

    @Override
    @PostMapping
    public ResponseEntity<AlunoResponse> create(@RequestBody AlunoRequest request) {
        AlunoResponse aluno = alunoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(aluno);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponse> update(@PathVariable Integer id, @RequestBody UpdateAlunoRequest request) {
        AlunoResponse aluno = alunoService.update(id, request);
        return ResponseEntity.ok(aluno);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        alunoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

