package com.faculdade.controller;

import com.faculdade.controller.interfaces.ICursoController;
import com.faculdade.dto.request.CursoRequest;
import com.faculdade.dto.response.CursoResponse;
import com.faculdade.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController implements ICursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CursoResponse>> findAll() {
        List<CursoResponse> cursos = cursoService.findAll();
        return ResponseEntity.ok(cursos);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> findById(@PathVariable Integer id) {
        CursoResponse curso = cursoService.findById(id);
        return ResponseEntity.ok(curso);
    }

    @Override
    @PostMapping
    public ResponseEntity<CursoResponse> create(@RequestBody CursoRequest request) {
        CursoResponse curso = cursoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(curso);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> update(@PathVariable Integer id, @RequestBody CursoRequest request) {
        CursoResponse curso = cursoService.update(id, request);
        return ResponseEntity.ok(curso);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


