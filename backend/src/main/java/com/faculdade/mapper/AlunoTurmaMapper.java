package com.faculdade.mapper;

import com.faculdade.dto.request.AlunoTurmaRequest;
import com.faculdade.dto.request.UpdateAlunoTurmaRequest;
import com.faculdade.dto.response.AlunoTurmaResponse;
import com.faculdade.entity.AlunoTurma;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AlunoTurmaMapper {

    @Mapping(target = "aluno", ignore = true)
    @Mapping(target = "turma", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notaFinal", ignore = true)
    @Mapping(target = "frequencia", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "MATRICULADO")
    AlunoTurma toEntity(AlunoTurmaRequest request);

    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.nome", target = "alunoNome")
    @Mapping(source = "aluno.matricula", target = "alunoMatricula")
    @Mapping(source = "turma.id", target = "turmaId")
    @Mapping(source = "turma.codigoTurma", target = "turmaCodigo")
    @Mapping(source = "turma.disciplina.nome", target = "turmaDisciplinaNome")
    AlunoTurmaResponse toResponse(AlunoTurma alunoTurma);

    @Mapping(target = "aluno", ignore = true)
    @Mapping(target = "turma", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(UpdateAlunoTurmaRequest request, @MappingTarget AlunoTurma alunoTurma);
}


