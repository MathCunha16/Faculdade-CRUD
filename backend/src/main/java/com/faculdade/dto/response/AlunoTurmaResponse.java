package com.faculdade.dto.response;

import com.faculdade.entity.enums.StatusMatricula;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AlunoTurmaResponse(
        Integer id,
        Integer alunoId,
        String alunoNome,
        Integer alunoMatricula,
        Integer turmaId,
        String turmaCodigo,
        String turmaDisciplinaNome,
        LocalDate dataMatricula,
        StatusMatricula status,
        BigDecimal notaFinal,
        BigDecimal frequencia,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}


