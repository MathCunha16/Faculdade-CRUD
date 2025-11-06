package com.faculdade.seeds;

import com.faculdade.entity.Aluno;
import com.faculdade.entity.AlunoTurma;
import com.faculdade.entity.Turma;
import com.faculdade.entity.enums.StatusMatricula;
import com.faculdade.repository.AlunoRepository;
import com.faculdade.repository.AlunoTurmaRepository;
import com.faculdade.repository.TurmaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Order(3)
@Component
public class AlunoTurmaSeeder implements CommandLineRunner {

    private final AlunoTurmaRepository alunoTurmaRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;

    public AlunoTurmaSeeder(AlunoTurmaRepository alunoTurmaRepository, AlunoRepository alunoRepository, TurmaRepository turmaRepository) {
        this.alunoTurmaRepository = alunoTurmaRepository;
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (alunoTurmaRepository.count() == 0) {
            Aluno aluno1 = alunoRepository.findById(1).orElse(null);
            Aluno aluno2 = alunoRepository.findById(2).orElse(null);
            Turma turma1 = turmaRepository.findById(1).orElse(null);
            Turma turma2 = turmaRepository.findById(2).orElse(null);
            Turma turma3 = turmaRepository.findById(3).orElse(null);

            if (aluno1 != null && aluno2 != null && turma1 != null && turma2 != null && turma3 != null) {
                AlunoTurma alunoTurma1 = new AlunoTurma();
                alunoTurma1.setAluno(aluno1);
                alunoTurma1.setTurma(turma1);
                alunoTurma1.setDataMatricula(LocalDate.of(2025, 8, 1));
                alunoTurma1.setStatus(StatusMatricula.MATRICULADO);

                AlunoTurma alunoTurma2 = new AlunoTurma();
                alunoTurma2.setAluno(aluno1);
                alunoTurma2.setTurma(turma3);
                alunoTurma2.setDataMatricula(LocalDate.of(2025, 8, 1));
                alunoTurma2.setStatus(StatusMatricula.MATRICULADO);

                AlunoTurma alunoTurma3 = new AlunoTurma();
                alunoTurma3.setAluno(aluno2);
                alunoTurma3.setTurma(turma2);
                alunoTurma3.setDataMatricula(LocalDate.of(2025, 8, 1));
                alunoTurma3.setStatus(StatusMatricula.MATRICULADO);

                List<AlunoTurma> alunoTurmas = Arrays.asList(alunoTurma1, alunoTurma2, alunoTurma3);
                alunoTurmaRepository.saveAll(alunoTurmas);
                System.out.println("Seeded " + alunoTurmas.size() + " alunoTurmas.");
            }
        }
    }
}
