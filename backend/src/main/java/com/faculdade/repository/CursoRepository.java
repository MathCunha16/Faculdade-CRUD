package com.faculdade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.faculdade.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

}