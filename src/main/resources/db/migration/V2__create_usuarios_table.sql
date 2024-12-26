-- Senha cadastrada: admin
CREATE TABLE usuarios (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          login VARCHAR(255) NOT NULL,
                          senha VARCHAR(255) NOT NULL,
                          role VARCHAR(50) NOT NULL,
                          jornada_id BIGINT,
                          CONSTRAINT fk_jornada FOREIGN KEY (jornada_id) REFERENCES jornadas(id) ON DELETE SET NULL
);

CREATE UNIQUE INDEX idx_usuarios_login ON usuarios(login);
CREATE UNIQUE INDEX idx_usuarios_email ON usuarios(email);

INSERT INTO usuarios (nome, email, login, senha, role, jornada_id)
VALUES
    ('Helton Oliveira', 'helton@gmail.com', 'admin', '$2a$10$88y7vwsOY3pCC00.iDK3E.PoIA.50Q/B6orU7YFZes7T2jC0hq9jO', 'ADMIN', 2);
