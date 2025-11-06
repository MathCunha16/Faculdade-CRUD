CREATE TABLE `aluno_turma` (
                               `id` INT NOT NULL AUTO_INCREMENT,
                               `id_aluno` INT NOT NULL,
                               `id_turma` INT NOT NULL,
                               `data_matricula` DATE NOT NULL,
                               `status` ENUM('MATRICULADO', 'APROVADO', 'REPROVADO', 'TRANCADO') NOT NULL DEFAULT 'MATRICULADO',
                               `nota_final` DECIMAL(4,2) DEFAULT NULL,
                               `frequencia` DECIMAL(5,2) DEFAULT NULL,
                               `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `UK_aluno_turma` (`id_aluno`, `id_turma`),
                               KEY `FK_aluno_turma_aluno` (`id_aluno`),
                               KEY `FK_aluno_turma_turma` (`id_turma`),
                               CONSTRAINT `FK_aluno_turma_aluno` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id`),
                               CONSTRAINT `FK_aluno_turma_turma` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id`)
) ENGINE=InnoDB;