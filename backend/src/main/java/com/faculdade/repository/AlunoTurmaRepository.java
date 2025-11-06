package com.faculdade.repository;

import com.faculdade.entity.AlunoTurma;
import com.faculdade.entity.enums.StatusMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoTurmaRepository extends JpaRepository<AlunoTurma, Integer> {

    Optional<AlunoTurma> findByAlunoIdAndTurmaId(Integer alunoId, Integer turmaId);

    boolean existsByAlunoIdAndTurmaId(Integer alunoId, Integer turmaId);

    List<AlunoTurma> findByAlunoId(Integer alunoId);

    List<AlunoTurma> findByTurmaId(Integer turmaId);

    List<AlunoTurma> findByStatus(StatusMatricula status);

    List<AlunoTurma> findByAlunoIdAndStatus(Integer alunoId, StatusMatricula status);

    List<AlunoTurma> findByTurmaIdAndStatus(Integer turmaId, StatusMatricula status);

    long countByTurmaId(Integer turmaId);

    long countByTurmaIdAndStatus(Integer turmaId, StatusMatricula status);
}


