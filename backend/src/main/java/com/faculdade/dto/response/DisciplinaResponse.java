package com.faculdade.dto.response;

import java.time.LocalDateTime;

public record DisciplinaResponse(
        Integer id,
        String nome,
        String codigo,
        Integer cargaHoraria,
        Integer cursoId,
        String cursoNome,
        Integer semestreRecomendado,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}


