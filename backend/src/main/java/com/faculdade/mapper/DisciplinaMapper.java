package com.faculdade.mapper;

import com.faculdade.dto.request.DisciplinaRequest;
import com.faculdade.dto.response.DisciplinaResponse;
import com.faculdade.entity.Disciplina;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DisciplinaMapper {

    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Disciplina toEntity(DisciplinaRequest request);

    @Mapping(source = "curso.id", target = "cursoId")
    @Mapping(source = "curso.nome", target = "cursoNome")
    DisciplinaResponse toResponse(Disciplina disciplina);

    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(DisciplinaRequest request, @MappingTarget Disciplina disciplina);
}


