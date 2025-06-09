-- Este script garante que a estrutura do banco exista e que os dados iniciais
-- sejam inseridos, sem apagar ou dar erro se já existirem.

-- CRIA AS TABELAS APENAS SE ELAS JÁ NÃO EXISTIREM
CREATE TABLE IF NOT EXISTS `curso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_curso_nome` (`nome`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `aluno` (
  `id` int NOT NULL AUTO_INCREMENT,
  `matricula` int NOT NULL,
  `nome` varchar(255) NOT NULL,
  `telefone` varchar(25) DEFAULT NULL,
  `data_de_nascimento` date NOT NULL,
  `id_curso` int NOT NULL,
  `cpf` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_aluno_matricula` (`matricula`),
  KEY `FK_aluno_curso` (`id_curso`),
  CONSTRAINT `FK_aluno_curso` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `tipo_usuario` varchar(25) NOT NULL,
  `aluno_matricula` int DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UK_usuarios_email` (`email`),
  KEY `FK_usuarios_aluno` (`aluno_matricula`),
  CONSTRAINT `FK_usuarios_aluno` FOREIGN KEY (`aluno_matricula`) REFERENCES `aluno` (`matricula`) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `turma` (
  `id_turma` int NOT NULL AUTO_INCREMENT,
  `nome_turma` varchar(100) NOT NULL,
  `turno` varchar(20) NOT NULL,
  `id_curso` int NOT NULL,
  `alunos` text,
  PRIMARY KEY (`id_turma`),
  KEY `FK_turma_curso` (`id_curso`),
  CONSTRAINT `FK_turma_curso` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `controle_matricula` (
  `ultima_matricula` int NOT NULL,
  PRIMARY KEY (`ultima_matricula`)
) ENGINE=InnoDB;


-- INSERE OS DADOS, IGNORANDO SE A CHAVE PRIMÁRIA JÁ EXISTIR
INSERT IGNORE INTO `curso` (`id`, `nome`) VALUES
(1, 'Tecnológia em Analíse e Desenvolvimento de Sistemas'),
(2, 'Sistemas de Informação'),
(3, 'Ciência da Computação'),
(4, 'Cinema'),
(5, 'Engenharia de Software'),
(6, 'Engenharia Civil'),
(7, 'Engenharia Elétrica'),
(8, 'Engenharia Mecânica'),
(9, 'Direito'),
(10, 'Administração'),
(11, 'Ciências Contábeis'),
(12, 'Economia'),
(13, 'Gestão Comercial'),
(14, 'Marketing'),
(15, 'Recursos Humanos'),
(16, 'Pedagogia'),
(17, 'Enfermagem'),
(18, 'Medicina'),
(19, 'Psicologia'),
(20, 'Educação Física'),
(21, 'Letras'),
(22, 'Design Gráfico'),
(23, 'Design de Interiores'),
(24, 'Arquitetura e Urbanismo'),
(25, 'Biologia'),
(26, 'Física'),
(27, 'Química'),
(28, 'Matemática');

INSERT IGNORE INTO `usuarios` (`id_usuario`, `email`, `senha`, `tipo_usuario`, `aluno_matricula`) VALUES (1, 'matheuscunhaprado@gmail.com', 'Cunha123', 'ADM', NULL);

INSERT IGNORE INTO `aluno` (`id`, `matricula`, `nome`, `telefone`, `data_de_nascimento`, `id_curso`, `cpf`) VALUES (1, 20250001, 'Silvio Vidal de Miranda Junior', '62 997632875', '1999-07-30', 5, '634.611.160-41');
INSERT IGNORE INTO `aluno` (`id`, `matricula`, `nome`, `telefone`, `data_de_nascimento`, `id_curso`, `cpf`) VALUES (2, 20250002, 'Gabirel Bortoleto da Silva', '62 998734555', '1980-08-18', 15, '555.094.420-00');

INSERT IGNORE INTO `usuarios` (`id_usuario`, `email`, `senha`, `tipo_usuario`, `aluno_matricula`) VALUES (2, 'silviovidal@gmail.com', 'MatheusLindo', 'ALUNO', 20250001);
INSERT IGNORE INTO `usuarios` (`id_usuario`, `email`, `senha`, `tipo_usuario`, `aluno_matricula`) VALUES (3, 'bortoleto@outlook.com', 'Bortoleto123', 'ALUNO', 20250002);

INSERT IGNORE INTO `turma` (`id_turma`, `nome_turma`, `turno`, `id_curso`, `alunos`) VALUES (1, 'Eng. de Software 2025/2 - Noturno', 'NOITE', 5, '20250001:Silvio Vidal de Miranda Junior');
INSERT IGNORE INTO `turma` (`id_turma`, `nome_turma`, `turno`, `id_curso`, `alunos`) VALUES (2, 'Gestão de Pessoas 2025 - Tarde', 'TARDE', 15, '20250002:Gabirel Bortoleto da Silva');
INSERT IGNORE INTO `turma` (`id_turma`, `nome_turma`, `turno`, `id_curso`, `alunos`) VALUES (3, 'Projetos Integrados 2025', 'MANHA', 5, '20250001:Silvio Vidal de Miranda Junior,20250002:Gabirel Bortoleto da Silva');

DELETE FROM `controle_matricula`;
INSERT IGNORE INTO `controle_matricula` (`ultima_matricula`) VALUES (20250002);