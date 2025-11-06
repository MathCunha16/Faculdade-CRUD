package com.faculdade.service;

import com.faculdade.dto.request.AlunoRequest;
import com.faculdade.dto.request.UpdateAlunoRequest;
import com.faculdade.dto.response.AlunoResponse;
import com.faculdade.entity.Aluno;
import com.faculdade.entity.Curso;
import com.faculdade.exception.ConflictException;
import com.faculdade.exception.ResourceNotFoundException;
import com.faculdade.mapper.AlunoMapper;
import com.faculdade.repository.AlunoRepository;
import com.faculdade.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final AlunoMapper alunoMapper;

    public AlunoService(AlunoRepository alunoRepository, CursoRepository cursoRepository, AlunoMapper alunoMapper) {
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
        this.alunoMapper = alunoMapper;
    }

    public List<AlunoResponse> findAll() {
        return alunoRepository.findAll().stream()
                .map(alunoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AlunoResponse findById(Integer id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", id));
        return alunoMapper.toResponse(aluno);
    }

    public AlunoResponse findByMatricula(Integer matricula) {
        Aluno aluno = alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", "matrícula", matricula));
        return alunoMapper.toResponse(aluno);
    }

    public AlunoResponse create(AlunoRequest request) {
        if (alunoRepository.existsByMatricula(request.matricula())) {
            throw new ConflictException("Já existe um aluno com a matrícula: " + request.matricula());
        }
        if (alunoRepository.existsByCpf(request.cpf())) {
            throw new ConflictException("Já existe um aluno com o CPF: " + request.cpf());
        }
        if (alunoRepository.existsByEmail(request.email())) {
            throw new ConflictException("Já existe um aluno com o email: " + request.email());
        }

        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso", request.cursoId()));

        Aluno aluno = alunoMapper.toEntity(request);
        aluno.setCurso(curso);
        aluno = alunoRepository.save(aluno);
        return alunoMapper.toResponse(aluno);
    }

    public AlunoResponse update(Integer id, UpdateAlunoRequest request) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", id));

        if (request.email() != null && !request.email().equals(aluno.getEmail()) 
                && alunoRepository.existsByEmail(request.email())) {
            throw new ConflictException("Já existe um aluno com o email: " + request.email());
        }

        if (request.cursoId() != null) {
            Curso curso = cursoRepository.findById(request.cursoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Curso", request.cursoId()));
            aluno.setCurso(curso);
        }

        alunoMapper.updateEntityFromRequest(request, aluno);
        aluno = alunoRepository.save(aluno);
        return alunoMapper.toResponse(aluno);
    }

    public void delete(Integer id) {
        if (!alunoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aluno", id);
        }
        alunoRepository.deleteById(id);
    }
}
