package com.faculdade.service;

import com.faculdade.dto.request.AlunoTurmaRequest;
import com.faculdade.dto.request.UpdateAlunoTurmaRequest;
import com.faculdade.dto.response.AlunoTurmaResponse;
import com.faculdade.entity.Aluno;
import com.faculdade.entity.AlunoTurma;
import com.faculdade.entity.Turma;
import com.faculdade.exception.BadRequestException;
import com.faculdade.exception.ConflictException;
import com.faculdade.exception.ResourceNotFoundException;
import com.faculdade.mapper.AlunoTurmaMapper;
import com.faculdade.repository.AlunoRepository;
import com.faculdade.repository.AlunoTurmaRepository;
import com.faculdade.repository.TurmaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlunoTurmaService {

    private final AlunoTurmaRepository alunoTurmaRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final AlunoTurmaMapper alunoTurmaMapper;

    public AlunoTurmaService(AlunoTurmaRepository alunoTurmaRepository, AlunoRepository alunoRepository,
                            TurmaRepository turmaRepository, AlunoTurmaMapper alunoTurmaMapper) {
        this.alunoTurmaRepository = alunoTurmaRepository;
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
        this.alunoTurmaMapper = alunoTurmaMapper;
    }

    public List<AlunoTurmaResponse> findAll() {
        return alunoTurmaRepository.findAll().stream()
                .map(alunoTurmaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AlunoTurmaResponse findById(Integer id) {
        AlunoTurma alunoTurma = alunoTurmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula", id));
        return alunoTurmaMapper.toResponse(alunoTurma);
    }

    public List<AlunoTurmaResponse> findByAlunoId(Integer alunoId) {
        return alunoTurmaRepository.findByAlunoId(alunoId).stream()
                .map(alunoTurmaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<AlunoTurmaResponse> findByTurmaId(Integer turmaId) {
        return alunoTurmaRepository.findByTurmaId(turmaId).stream()
                .map(alunoTurmaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AlunoTurmaResponse matricularAluno(AlunoTurmaRequest request) {
        if (alunoTurmaRepository.existsByAlunoIdAndTurmaId(request.alunoId(), request.turmaId())) {
            throw new ConflictException("Aluno já está matriculado nesta turma");
        }

        Aluno aluno = alunoRepository.findById(request.alunoId())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", request.alunoId()));

        Turma turma = turmaRepository.findById(request.turmaId())
                .orElseThrow(() -> new ResourceNotFoundException("Turma", request.turmaId()));

        // Verificar se há vagas disponíveis
        long alunosMatriculados = alunoTurmaRepository.countByTurmaIdAndStatus(
                request.turmaId(), 
                com.faculdade.entity.enums.StatusMatricula.MATRICULADO
        );
        if (alunosMatriculados >= turma.getVagasTotais()) {
            throw new BadRequestException("Não há vagas disponíveis nesta turma");
        }

        AlunoTurma alunoTurma = alunoTurmaMapper.toEntity(request);
        alunoTurma.setAluno(aluno);
        alunoTurma.setTurma(turma);
        alunoTurma = alunoTurmaRepository.save(alunoTurma);
        return alunoTurmaMapper.toResponse(alunoTurma);
    }

    public AlunoTurmaResponse atualizarNotas(Integer id, UpdateAlunoTurmaRequest request) {
        AlunoTurma alunoTurma = alunoTurmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula", id));

        alunoTurmaMapper.updateEntityFromRequest(request, alunoTurma);
        alunoTurma = alunoTurmaRepository.save(alunoTurma);
        return alunoTurmaMapper.toResponse(alunoTurma);
    }

    public void cancelarMatricula(Integer id) {
        AlunoTurma alunoTurma = alunoTurmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula", id));
        alunoTurmaRepository.delete(alunoTurma);
    }
}

