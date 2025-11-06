package com.faculdade.dto.response;

import com.faculdade.entity.enums.TipoUsuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Integer id,
        String email,
        TipoUsuario tipoUsuario,
        Integer alunoId,
        String alunoNome,
        Integer professorId,
        String professorNome,
        Boolean ativo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}


