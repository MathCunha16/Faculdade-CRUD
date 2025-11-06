package com.faculdade.service;

import com.faculdade.dto.request.LoginRequest;
import com.faculdade.dto.response.LoginResponse;
import com.faculdade.entity.Usuario;
import com.faculdade.entity.enums.TipoUsuario;
import com.faculdade.exception.UnauthorizedException;
import com.faculdade.repository.AlunoRepository;
import com.faculdade.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faculdade.dto.request.RegistroRequest;
import com.faculdade.dto.response.RegistroResponse;
import com.faculdade.entity.Aluno;
import com.faculdade.exception.BadRequestException;
import com.faculdade.exception.ConflictException;
import com.faculdade.exception.ResourceNotFoundException;
import com.faculdade.util.PasswordValidator;

@Service
@Transactional
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       AlunoRepository alunoRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.alunoRepository = alunoRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.senha()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Credenciais inválidas"));

        if (!usuario.getAtivo()) {
            throw new UnauthorizedException("Usuário inativo");
        }

        String token = jwtService.generateToken(usuario);

        String nome = usuario.getTipoUsuario() == TipoUsuario.ALUNO && usuario.getAluno() != null
                ? usuario.getAluno().getNome()
                : usuario.getTipoUsuario() == TipoUsuario.PROFESSOR && usuario.getProfessor() != null
                ? usuario.getProfessor().getNome()
                : "Administrador";

        return new LoginResponse(token, usuario.getId(), usuario.getEmail(), usuario.getTipoUsuario(), nome);
    }

    public RegistroResponse registrar(RegistroRequest request) {
        usuarioRepository.findByEmail(request.email()).ifPresent(u -> {
            throw new ConflictException("Email já cadastrado");
        });

        if (!PasswordValidator.isValid(request.senha())) {
            throw new BadRequestException(PasswordValidator.getValidationMessage());
        }

        Aluno aluno = alunoRepository.findById(request.matricula())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com a matrícula informada"));

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(request.email());
        novoUsuario.setSenha(passwordEncoder.encode(request.senha()));
        novoUsuario.setTipoUsuario(TipoUsuario.ALUNO);
        novoUsuario.setAluno(aluno);
        novoUsuario.setAtivo(true);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        return new RegistroResponse("Usuário registrado com sucesso!", usuarioSalvo.getId());
    }
}

