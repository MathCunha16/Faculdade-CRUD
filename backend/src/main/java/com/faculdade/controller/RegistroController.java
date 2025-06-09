package com.faculdade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.faculdade.model.Aluno;
import com.faculdade.model.RegistroRequest; // Vamos criar este DTO
import com.faculdade.model.TipoUsuario;
import com.faculdade.model.Usuario;
import com.faculdade.repository.AlunoRepository;
import com.faculdade.repository.UsuarioRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/registrar")
public class RegistroController {

    @Autowired
    private AlunoRepository alunoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<String> registrarUsuario(@RequestBody RegistroRequest registroRequest) {
        
        // 1 Verifica se a matricula fornecida existe na tabela de alunos
        Aluno aluno = alunoRepository.findByMatricula(registroRequest.getMatricula());
        if (aluno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matrícula não encontrada no sistema.");
        }

        // 2 Verifica se a matricula ja não foi registrada por outro usuario
        if (usuarioRepository.existsByMatricula(registroRequest.getMatricula())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Esta matrícula já possui um usuário registrado.");
        }
        
        // 3 Verifica se o email ja ta em uso
        if (usuarioRepository.findByEmail(registroRequest.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este email já está em uso.");
        }

        // 4 Se todas as validações passarem, cria o novo usuario
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(registroRequest.getEmail());
        novoUsuario.setSenha(registroRequest.getSenha());
        novoUsuario.setMatricula(registroRequest.getMatricula());
        novoUsuario.setTipoUsuario(TipoUsuario.ALUNO);

        usuarioRepository.save(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso! Você já pode fazer o login.");
    }
}