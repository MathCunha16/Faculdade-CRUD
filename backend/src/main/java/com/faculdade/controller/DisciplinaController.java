package com.faculdade.controller;

import com.faculdade.controller.interfaces.IDisciplinaController;
import com.faculdade.dto.request.DisciplinaRequest;
import com.faculdade.dto.response.DisciplinaResponse;
import com.faculdade.service.DisciplinaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController implements IDisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<DisciplinaResponse>> findAll() {
        List<DisciplinaResponse> disciplinas = disciplinaService.findAll();
        return ResponseEntity.ok(disciplinas);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaResponse> findById(@PathVariable Integer id) {
        DisciplinaResponse disciplina = disciplinaService.findById(id);
        return ResponseEntity.ok(disciplina);
    }

    @Override
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<DisciplinaResponse>> findByCursoId(@PathVariable Integer cursoId) {
        List<DisciplinaResponse> disciplinas = disciplinaService.findByCursoId(cursoId);
        return ResponseEntity.ok(disciplinas);
    }

    @Override
    @PostMapping
    public ResponseEntity<DisciplinaResponse> create(@RequestBody DisciplinaRequest request) {
        DisciplinaResponse disciplina = disciplinaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplina);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaResponse> update(@PathVariable Integer id, @RequestBody DisciplinaRequest request) {
        DisciplinaResponse disciplina = disciplinaService.update(id, request);
        return ResponseEntity.ok(disciplina);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        disciplinaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

