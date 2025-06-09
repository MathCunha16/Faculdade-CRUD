package com.faculdade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.faculdade.model.Aluno;
import com.faculdade.model.ControleMatricula;
import com.faculdade.repository.AlunoRepository;
import com.faculdade.repository.ControleMatriculaRepository;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ControleMatriculaRepository controleMatriculaRepository;

    @Transactional
    public Aluno criarNovoAluno(Aluno novoAluno) {
        // 1 Busca o registro de controle
        ControleMatricula controle = controleMatriculaRepository.findControle();
        if (controle == null) {
            // se o controle não existir, lança um erro, pois ele deveria ser criado pelo data.sql
            throw new IllegalStateException("Tabela de controle de matrícula não inicializada.");
        }

        // 2 Calcula a nova matricula
        int novaMatricula = controle.getUltima_matricula() + 1;

        // 3 Atualiza o valor no banco
        controleMatriculaRepository.setUltimaMatricula(novaMatricula);

        // 4 Seta a matricula no aluno e salva o aluno.
        novoAluno.setMatricula(novaMatricula);
        return alunoRepository.save(novoAluno);
    }
}