package com.faculdade.controller;

import com.faculdade.controller.interfaces.IProfessorController;
import com.faculdade.dto.request.ProfessorRequest;
import com.faculdade.dto.request.UpdateProfessorRequest;
import com.faculdade.dto.response.ProfessorResponse;
import com.faculdade.service.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController implements IProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProfessorResponse>> findAll() {
        List<ProfessorResponse> professores = professorService.findAll();
        return ResponseEntity.ok(professores);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponse> findById(@PathVariable Integer id) {
        ProfessorResponse professor = professorService.findById(id);
        return ResponseEntity.ok(professor);
    }

    @Override
    @PostMapping
    public ResponseEntity<ProfessorResponse> create(@RequestBody ProfessorRequest request) {
        ProfessorResponse professor = professorService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(professor);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponse> update(@PathVariable Integer id, @RequestBody UpdateProfessorRequest request) {
        ProfessorResponse professor = professorService.update(id, request);
        return ResponseEntity.ok(professor);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


