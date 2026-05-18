CREATE TABLE IF NOT EXISTS usuario (
    id                              INT AUTO_INCREMENT PRIMARY KEY,
    nome                            VARCHAR(100) NOT NULL,
    email                           VARCHAR(100) NOT NULL UNIQUE,
    senha                           VARCHAR(255) NOT NULL,

    role ENUM(
       'FUNCIONARIO',
       'GERENTE',
       'ADMIN'
       ) NOT NULL DEFAULT 'FUNCIONARIO',

    email_verificado                BOOLEAN NOT NULL DEFAULT FALSE,

    token_verificacao               VARCHAR(255) NULL,
    token_verificacao_expiracao     DATETIME NULL,

    token_recuperacao               VARCHAR(255) NULL,
    token_recuperacao_expiracao     DATETIME NULL,

    token_ultimo_envio              DATETIME NULL
);

-- Usuário para testes
INSERT INTO usuario (
    nome,
    email,
    senha,
    role,
    email_verificado,
    token_verificacao,
    token_verificacao_expiracao,
    token_recuperacao,
    token_recuperacao_expiracao,
    token_ultimo_envio
) VALUES (
    'Teste',
    'teste@gmail.com',
    '$2a$10$z/eXQYdYSLo7R4NVNPwZ9uFvDNn/bM2P9ulTgYP3MaGyPrCwDvRQ.',
    'FUNCIONARIO',
    TRUE,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL
);

SET GLOBAL event_scheduler = ON;
DELIMITER $$
CREATE EVENT IF NOT EXISTS limpar_usuarios_pendentes
    ON SCHEDULE EVERY 24 HOUR
    COMMENT 'Remove usuários que não confirmaram email'
    DO
    BEGIN
        DELETE FROM usuario
        WHERE email_verificado = FALSE
          AND token_verificacao_expiracao IS NOT NULL
          AND token_verificacao_expiracao < NOW();
    END$$
DELIMITER ;