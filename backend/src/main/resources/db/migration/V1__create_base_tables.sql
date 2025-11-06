CREATE TABLE `curso` (
                         `id` INT NOT NULL AUTO_INCREMENT,
                         `nome` VARCHAR(100) NOT NULL,
                         `codigo` VARCHAR(10) NOT NULL,
                         `duracao_semestres` INT NOT NULL,
                         `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_curso_nome` (`nome`),
                         UNIQUE KEY `UK_curso_codigo` (`codigo`)
) ENGINE=InnoDB;

CREATE TABLE `aluno` (
                         `id` INT NOT NULL AUTO_INCREMENT,
                         `matricula` INT NOT NULL,
                         `nome` VARCHAR(255) NOT NULL,
                         `cpf` VARCHAR(15) NOT NULL,
                         `email` VARCHAR(100) NOT NULL,
                         `telefone` VARCHAR(25) DEFAULT NULL,
                         `data_nascimento` DATE NOT NULL,
                         `id_curso` INT NOT NULL,
                         `status` ENUM('ATIVO', 'TRANCADO', 'FORMADO', 'DESISTENTE') NOT NULL DEFAULT 'ATIVO',
                         `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         `deleted_at` TIMESTAMP NULL DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_aluno_matricula` (`matricula`),
                         UNIQUE KEY `UK_aluno_cpf` (`cpf`),
                         UNIQUE KEY `UK_aluno_email` (`email`),
                         KEY `FK_aluno_curso` (`id_curso`),
                         CONSTRAINT `FK_aluno_curso` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`)
) ENGINE=InnoDB;