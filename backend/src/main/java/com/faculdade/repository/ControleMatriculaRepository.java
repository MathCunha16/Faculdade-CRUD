package com.faculdade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.faculdade.model.ControleMatricula;

@Repository
public interface ControleMatriculaRepository extends JpaRepository<ControleMatricula, Integer> {

    // JPQL para buscar a primeira (e única) entrada
    @Query("SELECT cm FROM ControleMatricula cm")
    ControleMatricula findControle();

    // JPQL para atualizar o valor da ultima_matricula
    @Modifying
    @Transactional
    @Query("UPDATE ControleMatricula cm SET cm.ultima_matricula = ?1")
    void setUltimaMatricula(int novaMatricula);
}