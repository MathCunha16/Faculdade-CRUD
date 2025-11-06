package com.faculdade.repository;

import com.faculdade.entity.Professor;
import com.faculdade.entity.enums.StatusProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    Optional<Professor> findByCpf(String cpf);

    Optional<Professor> findByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    List<Professor> findByStatus(StatusProfessor status);

    List<Professor> findByEspecialidadeContainingIgnoreCase(String especialidade);
}


