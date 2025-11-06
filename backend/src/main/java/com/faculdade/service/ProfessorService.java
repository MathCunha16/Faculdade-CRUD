package com.faculdade.service;

import com.faculdade.dto.request.ProfessorRequest;
import com.faculdade.dto.request.UpdateProfessorRequest;
import com.faculdade.dto.response.ProfessorResponse;
import com.faculdade.entity.Professor;
import com.faculdade.exception.ConflictException;
import com.faculdade.exception.ResourceNotFoundException;
import com.faculdade.mapper.ProfessorMapper;
import com.faculdade.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    public ProfessorService(ProfessorRepository professorRepository, ProfessorMapper professorMapper) {
        this.professorRepository = professorRepository;
        this.professorMapper = professorMapper;
    }

    public List<ProfessorResponse> findAll() {
        return professorRepository.findAll().stream()
                .map(professorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProfessorResponse findById(Integer id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", id));
        return professorMapper.toResponse(professor);
    }

    public ProfessorResponse create(ProfessorRequest request) {
        if (professorRepository.existsByCpf(request.cpf())) {
            throw new ConflictException("Já existe um professor com o CPF: " + request.cpf());
        }
        if (professorRepository.existsByEmail(request.email())) {
            throw new ConflictException("Já existe um professor com o email: " + request.email());
        }

        Professor professor = professorMapper.toEntity(request);
        professor = professorRepository.save(professor);
        return professorMapper.toResponse(professor);
    }

    public ProfessorResponse update(Integer id, UpdateProfessorRequest request) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", id));

        if (request.email() != null && !request.email().equals(professor.getEmail()) 
                && professorRepository.existsByEmail(request.email())) {
            throw new ConflictException("Já existe um professor com o email: " + request.email());
        }

        professorMapper.updateEntityFromRequest(request, professor);
        professor = professorRepository.save(professor);
        return professorMapper.toResponse(professor);
    }

    public void delete(Integer id) {
        if (!professorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Professor", id);
        }
        professorRepository.deleteById(id);
    }
}

