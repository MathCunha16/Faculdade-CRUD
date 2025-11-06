package com.faculdade.repository;

import com.faculdade.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {

    Optional<Disciplina> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Disciplina> findByCursoId(Integer cursoId);

    List<Disciplina> findByCursoIdAndSemestreRecomendado(Integer cursoId, Integer semestreRecomendado);

    List<Disciplina> findByNomeContainingIgnoreCase(String nome);
}

