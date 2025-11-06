package com.faculdade.seeds;

import com.faculdade.entity.Aluno;
import com.faculdade.entity.Curso;
import com.faculdade.entity.enums.StatusAluno;
import com.faculdade.repository.AlunoRepository;
import com.faculdade.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Order(2)
@Component
@Profile("dev")
public class AlunoSeeder implements CommandLineRunner {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public AlunoSeeder(AlunoRepository alunoRepository, CursoRepository cursoRepository) {
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (alunoRepository.count() == 0) {
            Curso curso5 = cursoRepository.findById(5).orElse(null);
            Curso curso15 = cursoRepository.findById(15).orElse(null);

            if (curso5 != null && curso15 != null) {
                Aluno aluno1 = new Aluno();
                aluno1.setMatricula(20250001);
                aluno1.setNome("Silvio Vidal de Miranda Junior");
                aluno1.setCpf("634.611.160-41");
                aluno1.setEmail("silviovidal@aluno.faculdade.edu.br");
                aluno1.setTelefone("62 99763-2875");
                aluno1.setDataNascimento(LocalDate.of(1999, 7, 30));
                aluno1.setCurso(curso5);
                aluno1.setStatus(StatusAluno.ATIVO);

                Aluno aluno2 = new Aluno();
                aluno2.setMatricula(20250002);
                aluno2.setNome("Gabriel Bortoleto da Silva");
                aluno2.setCpf("555.094.420-00");
                aluno2.setEmail("bortoleto@aluno.faculdade.edu.br");
                aluno2.setTelefone("62 99873-4555");
                aluno2.setDataNascimento(LocalDate.of(1980, 8, 18));
                aluno2.setCurso(curso15);
                aluno2.setStatus(StatusAluno.ATIVO);

                List<Aluno> alunos = Arrays.asList(aluno1, aluno2);
                alunoRepository.saveAll(alunos);
                System.out.println("Seeded " + alunos.size() + " alunos.");
            }
        }
    }
}
