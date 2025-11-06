package com.faculdade.dto.response;

import com.faculdade.entity.enums.TipoUsuario;

public record LoginResponse(
        String token,
        Integer usuarioId,
        String email,
        TipoUsuario tipoUsuario,
        String nome
) {
}

