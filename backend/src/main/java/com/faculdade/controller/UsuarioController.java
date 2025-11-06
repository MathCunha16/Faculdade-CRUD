package com.faculdade.controller;

import com.faculdade.controller.interfaces.IUsuarioController;
import com.faculdade.dto.request.UsuarioRequest;
import com.faculdade.dto.response.UsuarioResponse;
import com.faculdade.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController implements IUsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Integer id) {
        UsuarioResponse usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @Override
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponse> findByEmail(@PathVariable String email) {
        UsuarioResponse usuario = usuarioService.findByEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @Override
    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@RequestBody UsuarioRequest request) {
        UsuarioResponse usuario = usuarioService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @Override
    @PatchMapping("/{id}/status")
    public ResponseEntity<UsuarioResponse> atualizarStatus(@PathVariable Integer id, @RequestBody Boolean ativo) {
        UsuarioResponse usuario = usuarioService.atualizarStatus(id, ativo);
        return ResponseEntity.ok(usuario);
    }
}


