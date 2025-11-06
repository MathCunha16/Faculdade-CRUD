package com.faculdade.dto.response;

import java.time.LocalDateTime;

public record CursoResponse(
        Integer id,
        String nome,
        String codigo,
        Integer duracaoSemestres,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}


