package com.faculdade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.faculdade.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmailAndSenha(String email, String senha);

    boolean existsByAlunoId(Integer alunoId);
    
    Usuario findByEmail(String email);
}