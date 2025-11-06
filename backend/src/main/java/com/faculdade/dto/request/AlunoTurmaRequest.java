package com.faculdade.dto.request;

import com.faculdade.entity.enums.StatusMatricula;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record AlunoTurmaRequest(
        @NotNull(message = "O ID do aluno é obrigatório")
        Integer alunoId,

        @NotNull(message = "O ID da turma é obrigatório")
        Integer turmaId,

        @NotNull(message = "A data de matrícula é obrigatória")
        @PastOrPresent(message = "A data de matrícula deve ser uma data atual ou passada")
        LocalDate dataMatricula,

        StatusMatricula status
) {
}

