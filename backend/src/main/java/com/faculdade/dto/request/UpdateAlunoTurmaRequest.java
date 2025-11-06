package com.faculdade.dto.request;

import com.faculdade.entity.enums.StatusMatricula;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateAlunoTurmaRequest(
        LocalDate dataMatricula,
        StatusMatricula status,
        BigDecimal notaFinal,
        BigDecimal frequencia
) {
}

