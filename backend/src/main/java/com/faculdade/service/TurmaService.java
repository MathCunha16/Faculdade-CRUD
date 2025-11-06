package com.faculdade.service;

import com.faculdade.dto.request.TurmaRequest;
import com.faculdade.dto.response.TurmaResponse;
import com.faculdade.entity.Curso;
import com.faculdade.entity.Disciplina;
import com.faculdade.entity.Professor;
import com.faculdade.entity.Turma;
import com.faculdade.exception.ConflictException;
import com.faculdade.exception.ResourceNotFoundException;
import com.faculdade.mapper.TurmaMapper;
import com.faculdade.repository.CursoRepository;
import com.faculdade.repository.DisciplinaRepository;
import com.faculdade.repository.ProfessorRepository;
import com.faculdade.repository.TurmaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;
    private final CursoRepository cursoRepository;
    private final TurmaMapper turmaMapper;

    public TurmaService(TurmaRepository turmaRepository, DisciplinaRepository disciplinaRepository,
                        ProfessorRepository professorRepository, CursoRepository cursoRepository,
                        TurmaMapper turmaMapper) {
        this.turmaRepository = turmaRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
        this.cursoRepository = cursoRepository;
        this.turmaMapper = turmaMapper;
    }

    public List<TurmaResponse> findAll() {
        return turmaRepository.findAll().stream()
                .map(turmaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TurmaResponse findById(Integer id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma", id));
        return turmaMapper.toResponse(turma);
    }

    public List<TurmaResponse> findByDisciplinaId(Integer disciplinaId) {
        return turmaRepository.findByDisciplinaId(disciplinaId).stream()
                .map(turmaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<TurmaResponse> findByProfessorId(Integer professorId) {
        return turmaRepository.findByProfessorId(professorId).stream()
                .map(turmaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TurmaResponse create(TurmaRequest request) {
        if (turmaRepository.existsByCodigoTurma(request.codigoTurma())) {
            throw new ConflictException("Já existe uma turma com o código: " + request.codigoTurma());
        }

        Disciplina disciplina = disciplinaRepository.findById(request.disciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina", request.disciplinaId()));

        Professor professor = professorRepository.findById(request.professorId())
                .orElseThrow(() -> new ResourceNotFoundException("Professor", request.professorId()));

        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso", request.cursoId()));

        Turma turma = turmaMapper.toEntity(request);
        turma.setDisciplina(disciplina);
        turma.setProfessor(professor);
        turma.setCurso(curso);
        turma = turmaRepository.save(turma);
        return turmaMapper.toResponse(turma);
    }

    public TurmaResponse update(Integer id, TurmaRequest request) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma", id));

        if (!request.codigoTurma().equals(turma.getCodigoTurma()) 
                && turmaRepository.existsByCodigoTurma(request.codigoTurma())) {
            throw new ConflictException("Já existe uma turma com o código: " + request.codigoTurma());
        }

        if (request.disciplinaId() != null) {
            Disciplina disciplina = disciplinaRepository.findById(request.disciplinaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Disciplina", request.disciplinaId()));
            turma.setDisciplina(disciplina);
        }

        if (request.professorId() != null) {
            Professor professor = professorRepository.findById(request.professorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Professor", request.professorId()));
            turma.setProfessor(professor);
        }

        if (request.cursoId() != null) {
            Curso curso = cursoRepository.findById(request.cursoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Curso", request.cursoId()));
            turma.setCurso(curso);
        }

        turmaMapper.updateEntityFromRequest(request, turma);
        turma = turmaRepository.save(turma);
        return turmaMapper.toResponse(turma);
    }

    public void delete(Integer id) {
        if (!turmaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Turma", id);
        }
        turmaRepository.deleteById(id);
    }
}

