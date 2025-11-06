CREATE TABLE `professor` (
                             `id` INT NOT NULL AUTO_INCREMENT,
                             `nome` VARCHAR(255) NOT NULL,
                             `cpf` VARCHAR(15) NOT NULL,
                             `email` VARCHAR(100) NOT NULL,
                             `telefone` VARCHAR(25) DEFAULT NULL,
                             `especialidade` VARCHAR(100) DEFAULT NULL,
                             `status` ENUM('ATIVO', 'INATIVO') NOT NULL DEFAULT 'ATIVO',
                             `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `UK_professor_cpf` (`cpf`),
                             UNIQUE KEY `UK_professor_email` (`email`)
) ENGINE=InnoDB;