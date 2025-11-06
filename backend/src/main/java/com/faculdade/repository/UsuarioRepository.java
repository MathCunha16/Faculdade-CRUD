package com.faculdade.repository;

import com.faculdade.entity.Usuario;
import com.faculdade.entity.enums.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    boolean existsByEmail(String email);

    boolean existsByAlunoId(Integer alunoId);

    boolean existsByProfessorId(Integer professorId);

    List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);

    List<Usuario> findByAtivo(Boolean ativo);

    List<Usuario> findByTipoUsuarioAndAtivo(TipoUsuario tipoUsuario, Boolean ativo);

    Optional<Usuario> findByAlunoId(Integer alunoId);

    Optional<Usuario> findByProfessorId(Integer professorId);
}


