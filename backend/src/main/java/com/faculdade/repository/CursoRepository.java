package com.faculdade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.faculdade.model.Curso;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

}