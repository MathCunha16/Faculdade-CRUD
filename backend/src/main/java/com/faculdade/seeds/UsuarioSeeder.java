package com.faculdade.seeds;

import com.faculdade.dto.request.UsuarioRequest;
import com.faculdade.entity.Aluno;
import com.faculdade.entity.Professor;
import com.faculdade.entity.enums.TipoUsuario;
import com.faculdade.repository.AlunoRepository;
import com.faculdade.repository.ProfessorRepository;
import com.faculdade.repository.UsuarioRepository;
import com.faculdade.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class UsuarioSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;

    public UsuarioSeeder(UsuarioRepository usuarioRepository, UsuarioService usuarioService, AlunoRepository alunoRepository, ProfessorRepository professorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seed ADM User
        if (usuarioRepository.findByEmail("admin@faculdade.com").isEmpty()) {
            usuarioService.create(new UsuarioRequest(
                    "admin@faculdade.com",
                    "admin123",
                    TipoUsuario.ADM,
                    null,
                    null
            ));
            System.out.println("Seeded ADM user: admin@faculdade.com");
        }

        // Seed Professor User (for Professor with ID 2 from V7 migration)
        if (usuarioRepository.findByEmail("ana.santos@faculdade.edu.br").isEmpty()) {
            Professor professor = professorRepository.findById(2)
                    .orElseGet(() -> {
                        // Fallback if professor 2 doesn't exist (e.g., if V7 was modified/removed)
                        // In a real scenario, you might create the professor here or throw an error.
                        System.err.println("Professor with ID 2 not found for seeding. Skipping professor user creation.");
                        return null;
                    });

            if (professor != null) {
                usuarioService.create(new UsuarioRequest(
                        "ana.santos@faculdade.edu.br",
                        "prof123",
                        TipoUsuario.PROFESSOR,
                        null,
                        professor.getId()
                ));
                System.out.println("Seeded PROFESSOR user: ana.santos@faculdade.edu.br");
            }
        }

        // Seed Aluno User (for Aluno with ID 1 from V7 migration)
        if (usuarioRepository.findByEmail("silvio.vidal@aluno.faculdade.edu.br").isEmpty()) {
            Aluno aluno = alunoRepository.findById(1)
                    .orElseGet(() -> {
                        // Fallback if aluno 1 doesn't exist
                        System.err.println("Aluno with ID 1 not found for seeding. Skipping aluno user creation.");
                        return null;
                    });

            if (aluno != null) {
                usuarioService.create(new UsuarioRequest(
                        "silvio.vidal@aluno.faculdade.edu.br",
                        "aluno123",
                        TipoUsuario.ALUNO,
                        aluno.getId(),
                        null
                ));
                System.out.println("Seeded ALUNO user: silvio.vidal@aluno.faculdade.edu.br");
            }
        }
    }
}
