package com.faculdade.dto.response;

import com.faculdade.entity.enums.StatusProfessor;

import java.time.LocalDateTime;

public record ProfessorResponse(
        Integer id,
        String nome,
        String cpf,
        String email,
        String telefone,
        String especialidade,
        StatusProfessor status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

