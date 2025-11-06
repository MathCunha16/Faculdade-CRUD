package com.faculdade.dto.response;

import com.faculdade.entity.enums.StatusAluno;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AlunoResponse(
        Integer id,
        Integer matricula,
        String nome,
        String cpf,
        String email,
        String telefone,
        LocalDate dataNascimento,
        Integer cursoId,
        String cursoNome,
        StatusAluno status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}


