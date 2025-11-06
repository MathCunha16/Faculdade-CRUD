package com.faculdade.mapper;

import com.faculdade.dto.request.TurmaRequest;
import com.faculdade.dto.response.TurmaResponse;
import com.faculdade.entity.Turma;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TurmaMapper {

    @Mapping(target = "disciplina", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Turma toEntity(TurmaRequest request);

    @Mapping(source = "disciplina.id", target = "disciplinaId")
    @Mapping(source = "disciplina.nome", target = "disciplinaNome")
    @Mapping(source = "professor.id", target = "professorId")
    @Mapping(source = "professor.nome", target = "professorNome")
    @Mapping(source = "curso.id", target = "cursoId")
    @Mapping(source = "curso.nome", target = "cursoNome")
    TurmaResponse toResponse(Turma turma);

    @Mapping(target = "disciplina", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigoTurma", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(TurmaRequest request, @MappingTarget Turma turma);
}

