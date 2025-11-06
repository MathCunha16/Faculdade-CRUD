package com.faculdade.repository;

import com.faculdade.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    Optional<Curso> findByCodigo(String codigo);

    Optional<Curso> findByNome(String nome);

    boolean existsByCodigo(String codigo);

    boolean existsByNome(String nome);
}


