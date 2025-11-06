package com.faculdade.service;

import com.faculdade.dto.request.CursoRequest;
import com.faculdade.dto.response.CursoResponse;
import com.faculdade.entity.Curso;
import com.faculdade.exception.ConflictException;
import com.faculdade.exception.ResourceNotFoundException;
import com.faculdade.mapper.CursoMapper;
import com.faculdade.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;

    public CursoService(CursoRepository cursoRepository, CursoMapper cursoMapper) {
        this.cursoRepository = cursoRepository;
        this.cursoMapper = cursoMapper;
    }

    public List<CursoResponse> findAll() {
        return cursoRepository.findAll().stream()
                .map(cursoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CursoResponse findById(Integer id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", id));
        return cursoMapper.toResponse(curso);
    }

    public CursoResponse create(CursoRequest request) {
        if (cursoRepository.existsByCodigo(request.codigo())) {
            throw new ConflictException("Já existe um curso com o código: " + request.codigo());
        }
        if (cursoRepository.existsByNome(request.nome())) {
            throw new ConflictException("Já existe um curso com o nome: " + request.nome());
        }

        Curso curso = cursoMapper.toEntity(request);
        curso = cursoRepository.save(curso);
        return cursoMapper.toResponse(curso);
    }

    public CursoResponse update(Integer id, CursoRequest request) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", id));

        if (!request.codigo().equals(curso.getCodigo()) && cursoRepository.existsByCodigo(request.codigo())) {
            throw new ConflictException("Já existe um curso com o código: " + request.codigo());
        }
        if (!request.nome().equals(curso.getNome()) && cursoRepository.existsByNome(request.nome())) {
            throw new ConflictException("Já existe um curso com o nome: " + request.nome());
        }

        cursoMapper.updateEntityFromRequest(request, curso);
        curso = cursoRepository.save(curso);
        return cursoMapper.toResponse(curso);
    }

    public void delete(Integer id) {
        if (!cursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Curso", id);
        }
        cursoRepository.deleteById(id);
    }
}

