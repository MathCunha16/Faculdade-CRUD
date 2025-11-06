package com.faculdade.mapper;

import com.faculdade.dto.request.AlunoRequest;
import com.faculdade.dto.request.UpdateAlunoRequest;
import com.faculdade.dto.response.AlunoResponse;
import com.faculdade.entity.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AlunoMapper {

    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Aluno toEntity(AlunoRequest request);

    @Mapping(source = "curso.id", target = "cursoId")
    @Mapping(source = "curso.nome", target = "cursoNome")
    AlunoResponse toResponse(Aluno aluno);

    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "matricula", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(UpdateAlunoRequest request, @MappingTarget Aluno aluno);
}

