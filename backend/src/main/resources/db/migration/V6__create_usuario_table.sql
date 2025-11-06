CREATE TABLE `usuario` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `email` VARCHAR(100) NOT NULL,
                          `senha` VARCHAR(255) NOT NULL,
                          `tipo_usuario` ENUM('ADM', 'ALUNO', 'PROFESSOR') NOT NULL,
                          `aluno_id` INT DEFAULT NULL,
                          `professor_id` INT DEFAULT NULL,
                          `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
                          `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `UK_usuario_email` (`email`),
                          KEY `FK_usuario_aluno` (`aluno_id`),
                          KEY `FK_usuario_professor` (`professor_id`),
                          CONSTRAINT `FK_usuario_aluno` FOREIGN KEY (`aluno_id`) REFERENCES `aluno` (`id`) ON DELETE SET NULL,
                          CONSTRAINT `FK_usuario_professor` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

