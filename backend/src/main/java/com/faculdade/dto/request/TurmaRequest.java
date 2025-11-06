package com.faculdade.dto.request;

import com.faculdade.entity.enums.Turno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TurmaRequest(
        @NotBlank(message = "O código da turma é obrigatório")
        @Size(max = 50, message = "O código da turma deve ter no máximo 50 caracteres")
        String codigoTurma,

        @NotNull(message = "O ID da disciplina é obrigatório")
        Integer disciplinaId,

        @NotNull(message = "O ID do professor é obrigatório")
        Integer professorId,

        @NotNull(message = "O ID do curso é obrigatório")
        Integer cursoId,

        @NotNull(message = "O ano letivo é obrigatório")
        @Positive(message = "O ano letivo deve ser um valor positivo")
        Integer anoLetivo,

        @NotNull(message = "O semestre é obrigatório")
        @Positive(message = "O semestre deve ser um valor positivo")
        Integer semestre,

        @NotNull(message = "O turno é obrigatório")
        Turno turno,

        @NotNull(message = "As vagas totais são obrigatórias")
        @Positive(message = "As vagas totais devem ser um valor positivo")
        Integer vagasTotais
) {
}


