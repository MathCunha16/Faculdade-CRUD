package com.faculdade.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.faculdade.model.Aluno;
import com.faculdade.model.Turma;
import com.faculdade.repository.AlunoRepository;
import com.faculdade.repository.TurmaRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    @Autowired
    private TurmaRepository turmaRepository;
    
    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping
    public List<Turma> listarTodasTurmas() {
        return turmaRepository.findAll();
    }
    
    @PostMapping
    public ResponseEntity<Turma> criarTurma(@RequestBody Turma novaTurma) {
        Turma turmaSalva = turmaRepository.save(novaTurma);
        return ResponseEntity.status(201).body(turmaSalva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarTurma(@PathVariable Integer id) {
        return turmaRepository.findById(id)
            .map(turma -> {
                turmaRepository.delete(turma);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{idTurma}/alunos")
    public ResponseEntity<Turma> adicionarAluno(
            @PathVariable Integer idTurma,
            @RequestBody Map<String, Integer> payload) {
        
        Integer matriculaAluno = payload.get("matricula");
        Turma turma = turmaRepository.findById(idTurma).orElse(null);
        Aluno aluno = alunoRepository.findByMatricula(matriculaAluno);

        if (turma == null || aluno == null) {
            return ResponseEntity.notFound().build();
        }
        
        String alunosAtuaisStr = turma.getAlunosStr();
        String alunoParaAdicionar = aluno.getMatricula() + ":" + aluno.getNome();

        if (alunosAtuaisStr == null || !alunosAtuaisStr.contains(alunoParaAdicionar)) {
            String novaStringDeAlunos;
            if (alunosAtuaisStr == null || alunosAtuaisStr.isEmpty()) {
                novaStringDeAlunos = alunoParaAdicionar;
            } else {
                novaStringDeAlunos = alunosAtuaisStr + "," + alunoParaAdicionar;
            }
            turma.setAlunosStr(novaStringDeAlunos);
            turmaRepository.save(turma);
        }
        return ResponseEntity.ok(turma);
    }

    @DeleteMapping("/{idTurma}/alunos/{matriculaAluno}")
    public ResponseEntity<Turma> removerAluno(
            @PathVariable Integer idTurma,
            @PathVariable Integer matriculaAluno) {

        Turma turma = turmaRepository.findById(idTurma).orElse(null);
        if (turma == null) {
            return ResponseEntity.notFound().build();
        }

        String alunosAtuaisStr = turma.getAlunosStr();
        if (alunosAtuaisStr != null && !alunosAtuaisStr.isEmpty()) {
            List<String> alunosList = Arrays.asList(alunosAtuaisStr.split(","));
            List<String> novaListaDeAlunos = alunosList.stream()
                .filter(alunoStr -> !alunoStr.startsWith(matriculaAluno + ":"))
                .collect(Collectors.toList());
            String novaStringDeAlunos = String.join(",", novaListaDeAlunos);
            turma.setAlunosStr(novaStringDeAlunos);
            turmaRepository.save(turma);
        }
        return ResponseEntity.ok(turma);
    }
}