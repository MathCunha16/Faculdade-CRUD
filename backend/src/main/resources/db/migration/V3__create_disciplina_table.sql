CREATE TABLE `disciplina` (
                              `id` INT NOT NULL AUTO_INCREMENT,
                              `nome` VARCHAR(100) NOT NULL,
                              `codigo` VARCHAR(20) NOT NULL,
                              `carga_horaria` INT NOT NULL,
                              `id_curso` INT NOT NULL,
                              `semestre_recomendado` INT NOT NULL,
                              `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `UK_disciplina_codigo` (`codigo`),
                              KEY `FK_disciplina_curso` (`id_curso`),
                              CONSTRAINT `FK_disciplina_curso` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`)
) ENGINE=InnoDB;