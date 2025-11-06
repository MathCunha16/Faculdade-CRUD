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

INSERT INTO `disciplina` (`id`, `nome`, `codigo`, `carga_horaria`, `id_curso`, `semestre_recomendado`) VALUES
                                                                                                           (1, 'Programação Orientada a Objetos', 'ES101', 80, 5, 2),
                                                                                                           (2, 'Banco de Dados', 'ES201', 80, 5, 3),
                                                                                                           (3, 'Engenharia de Software', 'ES301', 80, 5, 5),
                                                                                                           (4, 'Gestão de Pessoas', 'RH101', 60, 15, 1),
                                                                                                           (5, 'Recrutamento e Seleção', 'RH201', 60, 15, 3),
                                                                                                           (6, 'Desenvolvimento de Carreira', 'RH301', 60, 15, 5);

INSERT INTO `professor` (`id`, `nome`, `cpf`, `email`, `telefone`, `especialidade`, `status`) VALUES
                                                                                                              (1, 'Dr. João Silva', '111.111.111-11', 'joao.silva@faculdade.edu', '(11) 98765-4321', 'Engenharia de Software', 'ATIVO'),
                                                                                                              (2, 'Dra. Maria Santos', '222.222.222-22', 'maria.santos@faculdade.edu', '(11) 98765-4322', 'Gestão de Pessoas', 'ATIVO'),
                                                                                                              (3, 'Prof. Carlos Oliveira', '333.333.333-33', 'carlos.oliveira@faculdade.edu', '(11) 98765-4323', 'Banco de Dados', 'ATIVO');

INSERT INTO `turma` (`id`, `codigo_turma`, `id_disciplina`, `id_professor`, `id_curso`, `ano_letivo`, `semestre`, `turno`, `vagas_totais`) VALUES
                                                                                                                                               (1, 'ES-2025/2-ES301-NOT', 3, 1, 5, 2025, 2, 'NOITE', 40),
                                                                                                                                               (2, 'RH-2025/2-RH101-TAR', 4, 2, 15, 2025, 2, 'TARDE', 35),
                                                                                                                                               (3, 'ES-2025/2-ES201-MAN', 2, 3, 5, 2025, 2, 'MANHA', 40);
