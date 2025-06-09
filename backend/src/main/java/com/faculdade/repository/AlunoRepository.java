package com.faculdade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faculdade.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    
    Aluno findByMatricula(Integer matricula);
    
    Aluno deleteByMatricula(Integer matricula);
    
    List<Aluno> findByNomeContainingIgnoreCase(String nome);
}