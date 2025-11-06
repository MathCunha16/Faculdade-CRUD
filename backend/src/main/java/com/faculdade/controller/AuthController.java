package com.faculdade.controller;

import com.faculdade.controller.interfaces.IAuthController;
import com.faculdade.dto.request.LoginRequest;
import com.faculdade.dto.request.RegistroRequest;
import com.faculdade.dto.response.LoginResponse;
import com.faculdade.dto.response.RegistroResponse;
import com.faculdade.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements IAuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/registrar")
    public ResponseEntity<RegistroResponse> registrar(@RequestBody RegistroRequest request) {
        RegistroResponse response = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

