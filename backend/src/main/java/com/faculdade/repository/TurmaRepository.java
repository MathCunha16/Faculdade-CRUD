package com.faculdade.repository;

import com.faculdade.entity.Turma;
import com.faculdade.entity.enums.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {

    Optional<Turma> findByCodigoTurma(String codigoTurma);

    boolean existsByCodigoTurma(String codigoTurma);

    List<Turma> findByDisciplinaId(Integer disciplinaId);

    List<Turma> findByProfessorId(Integer professorId);

    List<Turma> findByCursoId(Integer cursoId);

    List<Turma> findByAnoLetivoAndSemestre(Integer anoLetivo, Integer semestre);

    List<Turma> findByTurno(Turno turno);

    List<Turma> findByDisciplinaIdAndAnoLetivoAndSemestre(Integer disciplinaId, Integer anoLetivo, Integer semestre);

    List<Turma> findByProfessorIdAndAnoLetivoAndSemestre(Integer professorId, Integer anoLetivo, Integer semestre);

    List<Turma> findByCursoIdAndAnoLetivoAndSemestre(Integer cursoId, Integer anoLetivo, Integer semestre);
}

