package com.faculdade.mapper;

import com.faculdade.dto.request.ProfessorRequest;
import com.faculdade.dto.request.UpdateProfessorRequest;
import com.faculdade.dto.response.ProfessorResponse;
import com.faculdade.entity.Professor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProfessorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Professor toEntity(ProfessorRequest request);

    ProfessorResponse toResponse(Professor professor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(UpdateProfessorRequest request, @MappingTarget Professor professor);
}


