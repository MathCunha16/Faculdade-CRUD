package com.faculdade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.faculdade.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmailAndSenha(String email, String senha);

    boolean existsByMatricula(Integer matricula);
    
    Usuario findByEmail(String email);
}