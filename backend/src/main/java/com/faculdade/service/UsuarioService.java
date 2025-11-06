package com.faculdade.service;

import com.faculdade.dto.request.UsuarioRequest;
import com.faculdade.dto.response.UsuarioResponse;
import com.faculdade.entity.Aluno;
import com.faculdade.entity.Professor;
import com.faculdade.entity.Usuario;
import com.faculdade.exception.ConflictException;
import com.faculdade.exception.ResourceNotFoundException;
import com.faculdade.mapper.UsuarioMapper;
import com.faculdade.repository.AlunoRepository;
import com.faculdade.repository.ProfessorRepository;
import com.faculdade.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, AlunoRepository alunoRepository,
                         ProfessorRepository professorRepository, UsuarioMapper usuarioMapper,
                         PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse findById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "email", email));
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse create(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new ConflictException("Já existe um usuário com o email: " + request.email());
        }

        if (request.alunoId() != null && usuarioRepository.existsByAlunoId(request.alunoId())) {
            throw new ConflictException("Já existe um usuário para este aluno");
        }

        if (request.professorId() != null && usuarioRepository.existsByProfessorId(request.professorId())) {
            throw new ConflictException("Já existe um usuário para este professor");
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setSenha(passwordEncoder.encode(request.senha()));

        if (request.alunoId() != null) {
            Aluno aluno = alunoRepository.findById(request.alunoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Aluno", request.alunoId()));
            usuario.setAluno(aluno);
        }

        if (request.professorId() != null) {
            Professor professor = professorRepository.findById(request.professorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Professor", request.professorId()));
            usuario.setProfessor(professor);
        }

        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse atualizarStatus(Integer id, Boolean ativo) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
        usuario.setAtivo(ativo);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(usuario);
    }
}

