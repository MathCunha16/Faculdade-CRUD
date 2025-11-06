package com.faculdade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faculdade.model.Turma;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {
	
	List<Turma> findByAlunosStrContaining(String textoDoAluno);
	
}