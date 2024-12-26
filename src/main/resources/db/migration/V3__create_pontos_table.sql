CREATE TABLE pontos (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        data DATE NOT NULL,
                        hora_entrada TIME NOT NULL,
                        pausa_almoco TIME,
                        retorno_almoco TIME,
                        hora_saida TIME,
                        horas_trabalhadas BIGINT,
                        horas_excedentes BIGINT,
                        usuario_id BIGINT NOT NULL,
                        jornada_id BIGINT NOT NULL,
                        CONSTRAINT fk_ponto_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
                        CONSTRAINT fk_ponto_jornada FOREIGN KEY (jornada_id) REFERENCES jornadas(id) ON DELETE CASCADE
);

CREATE INDEX idx_pontos_data ON pontos(data);