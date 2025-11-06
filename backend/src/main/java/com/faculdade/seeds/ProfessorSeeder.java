package com.faculdade.seeds;

import com.faculdade.entity.Professor;
import com.faculdade.entity.enums.StatusProfessor;
import com.faculdade.repository.ProfessorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Order(1)
@Component
@Profile("dev")
public class ProfessorSeeder implements CommandLineRunner {

    private final ProfessorRepository professorRepository;

    public ProfessorSeeder(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (professorRepository.count() == 0) {
            Professor professor1 = new Professor();
            professor1.setNome("Dr. Carlos Alberto Silva");
            professor1.setCpf("123.456.789-00");
            professor1.setEmail("carlos.silva@faculdade.edu.br");
            professor1.setTelefone("62 99876-5432");
            professor1.setEspecialidade("Engenharia de Software");
            professor1.setStatus(StatusProfessor.ATIVO);

            Professor professor2 = new Professor();
            professor2.setNome("Profa. Ana Paula Santos");
            professor2.setCpf("987.654.321-00");
            professor2.setEmail("ana.santos@faculdade.edu.br");
            professor2.setTelefone("62 99765-4321");
            professor2.setEspecialidade("Gestão de Pessoas");
            professor2.setStatus(StatusProfessor.ATIVO);

            Professor professor3 = new Professor();
            professor3.setNome("Prof. Roberto Lima");
            professor3.setCpf("456.789.123-00");
            professor3.setEmail("roberto.lima@faculdade.edu.br");
            professor3.setTelefone("62 99654-3210");
            professor3.setEspecialidade("Banco de Dados");
            professor3.setStatus(StatusProfessor.ATIVO);

            List<Professor> professors = Arrays.asList(professor1, professor2, professor3);
            professorRepository.saveAll(professors);
            System.out.println("Seeded " + professors.size() + " professors.");
        }
    }
}
