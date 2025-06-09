package com.faculdade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faculdade.model.LoginRequest;
import com.faculdade.model.Usuario;
import com.faculdade.repository.UsuarioRepository; 

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody LoginRequest loginRequest) {    

        Usuario usuarioAutenticado = usuarioRepository.findByEmailAndSenha(
            loginRequest.getEmail(), 
            loginRequest.getSenha()
        );

        if (usuarioAutenticado != null) {
            usuarioAutenticado.setSenha(null); 
            return ResponseEntity.ok(usuarioAutenticado);
        } else {
            return ResponseEntity.status(401).body("Email ou senha inválidos.");
        }
    }
}