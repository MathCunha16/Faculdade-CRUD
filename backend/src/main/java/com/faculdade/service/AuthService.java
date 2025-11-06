package com.faculdade.service;

import com.faculdade.dto.request.LoginRequest;
import com.faculdade.dto.request.RegistroRequest;
import com.faculdade.dto.response.LoginResponse;
import com.faculdade.dto.response.RegistroResponse;
import com.faculdade.entity.Aluno;
import com.faculdade.entity.Usuario;
import com.faculdade.entity.enums.TipoUsuario;
import com.faculdade.exception.BadRequestException;
import com.faculdade.exception.ConflictException;
import com.faculdade.exception.ResourceNotFoundException;
import com.faculdade.exception.UnauthorizedException;
import com.faculdade.repository.AlunoRepository;
import com.faculdade.repository.UsuarioRepository;
import com.faculdade.util.PasswordValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, AlunoRepository alunoRepository,
                      PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.alunoRepository = alunoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Credenciais inválidas"));

        if (!usuario.getAtivo()) {
            throw new UnauthorizedException("Usuário inativo");
        }

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new UnauthorizedException("Credenciais inválidas");
        }

        // TODO: Gerar token JWT aqui
        String token = "token-jwt-aqui"; // Placeholder

        String nome = usuario.getTipoUsuario() == TipoUsuario.ALUNO && usuario.getAluno() != null
                ? usuario.getAluno().getNome()
                : usuario.getTipoUsuario() == TipoUsuario.PROFESSOR && usuario.getProfessor() != null
                ? usuario.getProfessor().getNome()
                : "Administrador";

        return new LoginResponse(
                token,
                usuario.getId(),
                usuario.getEmail(),
                usuario.getTipoUsuario(),
                nome
        );
    }

    public RegistroResponse registrar(RegistroRequest request) {
        // Validação adicional de senha (além das anotações)
        if (!PasswordValidator.isValid(request.senha())) {
            throw new BadRequestException(PasswordValidator.getValidationMessage());
        }

        Aluno aluno = alunoRepository.findByMatricula(request.matricula())
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula", "matrícula", request.matricula()));

        if (usuarioRepository.existsByAlunoId(aluno.getId())) {
            throw new ConflictException("Esta matrícula já possui um usuário registrado");
        }

        if (usuarioRepository.existsByEmail(request.email())) {
            throw new ConflictException("Este email já está em uso");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.email());
        // A senha é criptografada com BCrypt antes de salvar
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        usuario.setTipoUsuario(TipoUsuario.ALUNO);
        usuario.setAluno(aluno);
        usuario.setAtivo(true);

        usuario = usuarioRepository.save(usuario);

        return new RegistroResponse(
                "Usuário registrado com sucesso! Você já pode fazer o login.",
                usuario.getId()
        );
    }
}

