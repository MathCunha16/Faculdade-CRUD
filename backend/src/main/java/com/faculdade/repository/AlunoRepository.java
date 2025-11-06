package com.faculdade.repository;

import com.faculdade.entity.Aluno;
import com.faculdade.entity.enums.StatusAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    Optional<Aluno> findByMatricula(Integer matricula);

    Optional<Aluno> findByCpf(String cpf);

    Optional<Aluno> findByEmail(String email);

    boolean existsByMatricula(Integer matricula);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    List<Aluno> findByStatus(StatusAluno status);

    List<Aluno> findByCursoId(Integer cursoId);

    List<Aluno> findByCursoIdAndStatus(Integer cursoId, StatusAluno status);
}

