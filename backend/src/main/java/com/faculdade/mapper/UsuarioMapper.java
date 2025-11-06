package com.faculdade.mapper;

import com.faculdade.dto.request.UsuarioRequest;
import com.faculdade.dto.response.UsuarioResponse;
import com.faculdade.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UsuarioMapper {

    @Mapping(target = "aluno", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "senha", ignore = true)
    Usuario toEntity(UsuarioRequest request);

    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.nome", target = "alunoNome")
    @Mapping(source = "professor.id", target = "professorId")
    @Mapping(source = "professor.nome", target = "professorNome")
    UsuarioResponse toResponse(Usuario usuario);

    @Mapping(target = "aluno", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "senha", ignore = true)
    void updateEntityFromRequest(UsuarioRequest request, @MappingTarget Usuario usuario);
}


