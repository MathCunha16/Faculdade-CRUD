package com.faculdade.dto.request;

import com.faculdade.entity.enums.StatusProfessor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateProfessorRequest(
        @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres")
        String nome,

        @Email(message = "O email deve ser válido")
        @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
        String email,

        @Size(max = 25, message = "O telefone deve ter no máximo 25 caracteres")
        String telefone,

        @Size(max = 100, message = "A especialidade deve ter no máximo 100 caracteres")
        String especialidade,

        StatusProfessor status
) {
}


