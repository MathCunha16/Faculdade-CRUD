package com.faculdade.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DisciplinaRequest(
        @NotBlank(message = "O nome da disciplina é obrigatório")
        @Size(max = 100, message = "O nome da disciplina deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "O código da disciplina é obrigatório")
        @Size(max = 20, message = "O código da disciplina deve ter no máximo 20 caracteres")
        String codigo,

        @NotNull(message = "A carga horária é obrigatória")
        @Positive(message = "A carga horária deve ser um valor positivo")
        Integer cargaHoraria,

        @NotNull(message = "O ID do curso é obrigatório")
        Integer cursoId,

        @NotNull(message = "O semestre recomendado é obrigatório")
        @Positive(message = "O semestre recomendado deve ser um valor positivo")
        Integer semestreRecomendado
) {
}

