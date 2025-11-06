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
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Order(4)
@Component
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
        if (usuarioRepository.findByEmail("matheuscunhaprado@gmail.com").isEmpty()) {
            usuarioService.create(new UsuarioRequest(
                    "matheuscunhaprado@gmail.com",
                    "Cunha123",
                    TipoUsuario.ADM,
                    null,
                    null
            ));
            System.out.println("Seeded ADM user: matheuscunhaprado@gmail.com");
        }

        // Seed Professor Users
        professorRepository.findAll().forEach(professor -> {
            if (usuarioRepository.findByEmail(professor.getEmail()).isEmpty()) {
                usuarioService.create(new UsuarioRequest(
                        professor.getEmail(),
                        "Prof123",
                        TipoUsuario.PROFESSOR,
                        null,
                        professor.getId()
                ));
                System.out.println("Seeded PROFESSOR user: " + professor.getEmail());
            }
        });

            // Seed Aluno Users
            alunoRepository.findAll().forEach(aluno -> {
                if (usuarioRepository.findByEmail(aluno.getEmail()).isEmpty()) {
                    usuarioService.create(new UsuarioRequest(
                            aluno.getEmail(),
                            "Aluno123",
                            TipoUsuario.ALUNO,
                            aluno.getId(),
                            null
                    ));
                    System.out.println("Seeded ALUNO user: " + aluno.getEmail());
                }
            });
        }
    }
