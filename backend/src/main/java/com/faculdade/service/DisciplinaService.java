package com.faculdade.service;

import com.faculdade.dto.request.DisciplinaRequest;
import com.faculdade.dto.response.DisciplinaResponse;
import com.faculdade.entity.Curso;
import com.faculdade.entity.Disciplina;
import com.faculdade.exception.ConflictException;
import com.faculdade.exception.ResourceNotFoundException;
import com.faculdade.mapper.DisciplinaMapper;
import com.faculdade.repository.CursoRepository;
import com.faculdade.repository.DisciplinaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final CursoRepository cursoRepository;
    private final DisciplinaMapper disciplinaMapper;

    public DisciplinaService(DisciplinaRepository disciplinaRepository, CursoRepository cursoRepository, 
                             DisciplinaMapper disciplinaMapper) {
        this.disciplinaRepository = disciplinaRepository;
        this.cursoRepository = cursoRepository;
        this.disciplinaMapper = disciplinaMapper;
    }

    public List<DisciplinaResponse> findAll() {
        return disciplinaRepository.findAll().stream()
                .map(disciplinaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DisciplinaResponse findById(Integer id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina", id));
        return disciplinaMapper.toResponse(disciplina);
    }

    public List<DisciplinaResponse> findByCursoId(Integer cursoId) {
        return disciplinaRepository.findByCursoId(cursoId).stream()
                .map(disciplinaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DisciplinaResponse create(DisciplinaRequest request) {
        if (disciplinaRepository.existsByCodigo(request.codigo())) {
            throw new ConflictException("Já existe uma disciplina com o código: " + request.codigo());
        }

        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso", request.cursoId()));

        Disciplina disciplina = disciplinaMapper.toEntity(request);
        disciplina.setCurso(curso);
        disciplina = disciplinaRepository.save(disciplina);
        return disciplinaMapper.toResponse(disciplina);
    }

    public DisciplinaResponse update(Integer id, DisciplinaRequest request) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina", id));

        if (!request.codigo().equals(disciplina.getCodigo()) && disciplinaRepository.existsByCodigo(request.codigo())) {
            throw new ConflictException("Já existe uma disciplina com o código: " + request.codigo());
        }

        if (request.cursoId() != null) {
            Curso curso = cursoRepository.findById(request.cursoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Curso", request.cursoId()));
            disciplina.setCurso(curso);
        }

        disciplinaMapper.updateEntityFromRequest(request, disciplina);
        disciplina = disciplinaRepository.save(disciplina);
        return disciplinaMapper.toResponse(disciplina);
    }

    public void delete(Integer id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Disciplina", id);
        }
        disciplinaRepository.deleteById(id);
    }
}

