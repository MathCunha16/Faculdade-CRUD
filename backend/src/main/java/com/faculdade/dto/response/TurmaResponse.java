package com.faculdade.dto.response;

import com.faculdade.entity.enums.Turno;

import java.time.LocalDateTime;

public record TurmaResponse(
        Integer id,
        String codigoTurma,
        Integer disciplinaId,
        String disciplinaNome,
        Integer professorId,
        String professorNome,
        Integer cursoId,
        String cursoNome,
        Integer anoLetivo,
        Integer semestre,
        Turno turno,
        Integer vagasTotais,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}


