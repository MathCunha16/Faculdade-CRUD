CREATE TABLE `curso` (
                         `id` INT NOT NULL AUTO_INCREMENT,
                         `nome` VARCHAR(100) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_curso_nome` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `aluno` (
                         `id` INT NOT NULL AUTO_INCREMENT,
                         `matricula` INT NOT NULL,
                         `nome` VARCHAR(255) NOT NULL,
                         `telefone` VARCHAR(25) DEFAULT NULL,
                         `data_de_nascimento` DATE NOT NULL,
                         `id_curso` INT NOT NULL,
                         `cpf` VARCHAR(15) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_aluno_matricula` (`matricula`),
                         UNIQUE KEY `UK_aluno_cpf` (`cpf`),
                         KEY `FK_aluno_curso` (`id_curso`),
                         CONSTRAINT `FK_aluno_curso` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `controle_matricula` (
                                      `id` INT NOT NULL,
                                      `ultima_matricula` VARCHAR(255) NOT NULL,
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;