CREATE TABLE jornadas (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          tipo_jornada VARCHAR(50) NOT NULL
);

CREATE INDEX idx_jornadas_tipo_jornada ON jornadas(tipo_jornada);

INSERT INTO jornadas (tipo_jornada)
VALUES
    ('SEIS_HORAS'),
    ('OITO_HORAS');
