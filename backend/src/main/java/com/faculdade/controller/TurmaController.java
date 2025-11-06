package com.faculdade.controller;

import com.faculdade.controller.interfaces.ITurmaController;
import com.faculdade.dto.request.TurmaRequest;
import com.faculdade.dto.response.TurmaResponse;
import com.faculdade.service.TurmaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController implements ITurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TurmaResponse>> findAll() {
        List<TurmaResponse> turmas = turmaService.findAll();
        return ResponseEntity.ok(turmas);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponse> findById(@PathVariable Integer id) {
        TurmaResponse turma = turmaService.findById(id);
        return ResponseEntity.ok(turma);
    }

    @Override
    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<List<TurmaResponse>> findByDisciplinaId(@PathVariable Integer disciplinaId) {
        List<TurmaResponse> turmas = turmaService.findByDisciplinaId(disciplinaId);
        return ResponseEntity.ok(turmas);
    }

    @Override
    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<TurmaResponse>> findByProfessorId(@PathVariable Integer professorId) {
        List<TurmaResponse> turmas = turmaService.findByProfessorId(professorId);
        return ResponseEntity.ok(turmas);
    }

    @Override
    @PostMapping
    public ResponseEntity<TurmaResponse> create(@RequestBody TurmaRequest request) {
        TurmaResponse turma = turmaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(turma);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<TurmaResponse> update(@PathVariable Integer id, @RequestBody TurmaRequest request) {
        TurmaResponse turma = turmaService.update(id, request);
        return ResponseEntity.ok(turma);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        turmaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

