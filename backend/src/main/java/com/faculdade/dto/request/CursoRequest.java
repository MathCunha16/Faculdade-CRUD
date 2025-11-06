package com.faculdade.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CursoRequest(
        @NotBlank(message = "O nome do curso é obrigatório")
        @Size(max = 100, message = "O nome do curso deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "O código do curso é obrigatório")
        @Size(max = 10, message = "O código do curso deve ter no máximo 10 caracteres")
        String codigo,

        @NotNull(message = "A duração em semestres é obrigatória")
        @Positive(message = "A duração em semestres deve ser um valor positivo")
        Integer duracaoSemestres
) {
}

