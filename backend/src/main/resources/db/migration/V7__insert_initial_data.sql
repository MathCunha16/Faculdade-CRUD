INSERT INTO `curso` (`id`, `nome`, `codigo`, `duracao_semestres`) VALUES
                                                                      (1, 'Tecnologia em Análise e Desenvolvimento de Sistemas', 'ADS', 6),
                                                                      (2, 'Sistemas de Informação', 'SI', 8),
                                                                      (3, 'Ciência da Computação', 'CC', 8),
                                                                      (4, 'Cinema', 'CIN', 8),
                                                                      (5, 'Engenharia de Software', 'ES', 10),
                                                                      (6, 'Engenharia Civil', 'EC', 10),
                                                                      (7, 'Engenharia Elétrica', 'EE', 10),
                                                                      (8, 'Engenharia Mecânica', 'EM', 10),
                                                                      (9, 'Direito', 'DIR', 10),
                                                                      (10, 'Administração', 'ADM', 8),
                                                                      (11, 'Ciências Contábeis', 'CONT', 8),
                                                                      (12, 'Economia', 'ECON', 8),
                                                                      (13, 'Gestão Comercial', 'GC', 6),
                                                                      (14, 'Marketing', 'MKT', 6),
                                                                      (15, 'Recursos Humanos', 'RH', 6),
                                                                      (16, 'Pedagogia', 'PED', 8),
                                                                      (17, 'Enfermagem', 'ENF', 10),
                                                                      (18, 'Medicina', 'MED', 12),
                                                                      (19, 'Psicologia', 'PSI', 10),
                                                                      (20, 'Educação Física', 'EDF', 8);

INSERT INTO `professor` (`id`, `nome`, `cpf`, `email`, `telefone`, `especialidade`, `status`) VALUES
                                                                                                  (1, 'Dr. Carlos Alberto Silva', '123.456.789-00', 'carlos.silva@faculdade.edu.br', '62 99876-5432', 'Engenharia de Software', 'ATIVO'),
                                                                                                  (2, 'Profa. Ana Paula Santos', '987.654.321-00', 'ana.santos@faculdade.edu.br', '62 99765-4321', 'Gestão de Pessoas', 'ATIVO'),
                                                                                                  (3, 'Prof. Roberto Lima', '456.789.123-00', 'roberto.lima@faculdade.edu.br', '62 99654-3210', 'Banco de Dados', 'ATIVO');

INSERT INTO `disciplina` (`id`, `nome`, `codigo`, `carga_horaria`, `id_curso`, `semestre_recomendado`) VALUES
                                                                                                           (1, 'Programação Orientada a Objetos', 'ES101', 80, 5, 2),
                                                                                                           (2, 'Banco de Dados', 'ES201', 80, 5, 3),
                                                                                                           (3, 'Engenharia de Software', 'ES301', 80, 5, 5),
                                                                                                           (4, 'Gestão de Pessoas', 'RH101', 60, 15, 1),
                                                                                                           (5, 'Recrutamento e Seleção', 'RH201', 60, 15, 3),
                                                                                                           (6, 'Desenvolvimento de Carreira', 'RH301', 60, 15, 5);

INSERT INTO `aluno` (`id`, `matricula`, `nome`, `cpf`, `email`, `telefone`, `data_nascimento`, `id_curso`, `status`) VALUES
                                                                                                                         (1, 20250001, 'Silvio Vidal de Miranda Junior', '634.611.160-41', 'silviovidal@aluno.faculdade.edu.br', '62 99763-2875', '1999-07-30', 5, 'ATIVO'),
                                                                                                                         (2, 20250002, 'Gabriel Bortoleto da Silva', '555.094.420-00', 'bortoleto@aluno.faculdade.edu.br', '62 99873-4555', '1980-08-18', 15, 'ATIVO');

INSERT INTO `turma` (`id`, `codigo_turma`, `id_disciplina`, `id_professor`, `id_curso`, `ano_letivo`, `semestre`, `turno`, `vagas_totais`) VALUES
                                                                                                                                               (1, 'ES-2025/2-ES301-NOT', 3, 1, 5, 2025, 2, 'NOITE', 40),
                                                                                                                                               (2, 'RH-2025/2-RH101-TAR', 4, 2, 15, 2025, 2, 'TARDE', 35),
                                                                                                                                               (3, 'ES-2025/2-ES201-MAN', 2, 3, 5, 2025, 2, 'MANHA', 40);

INSERT INTO `aluno_turma` (`id`, `id_aluno`, `id_turma`, `data_matricula`, `status`) VALUES
                                                                                         (1, 1, 1, '2025-08-01', 'MATRICULADO'),
                                                                                         (2, 1, 3, '2025-08-01', 'MATRICULADO'),
                                                                                         (3, 2, 2, '2025-08-01', 'MATRICULADO');

INSERT INTO `usuario` (`id`, `email`, `senha`, `tipo_usuario`, `aluno_id`, `professor_id`, `ativo`)
VALUES (1, 'matheuscunhaprado@gmail.com', 'Cunha123', 'ADM', NULL, NULL, TRUE);

INSERT INTO `usuario` (`id`, `email`, `senha`, `tipo_usuario`, `aluno_id`, `professor_id`, `ativo`) VALUES
                                                                                                         (2, 'silviovidal@aluno.faculdade.edu.br', 'MatheusLindo', 'ALUNO', 1, NULL, TRUE),
                                                                                                         (3, 'bortoleto@aluno.faculdade.edu.br', 'Bortoleto123', 'ALUNO', 2, NULL, TRUE);

INSERT INTO `usuario` (`id`, `email`, `senha`, `tipo_usuario`, `aluno_id`, `professor_id`, `ativo`) VALUES
                                                                                                         (4, 'carlos.silva@faculdade.edu.br', 'Prof123', 'PROFESSOR', NULL, 1, TRUE),
                                                                                                         (5, 'ana.santos@faculdade.edu.br', 'Prof123', 'PROFESSOR', NULL, 2, TRUE),
                                                                                                         (6, 'roberto.lima@faculdade.edu.br', 'Prof123', 'PROFESSOR', NULL, 3, TRUE);