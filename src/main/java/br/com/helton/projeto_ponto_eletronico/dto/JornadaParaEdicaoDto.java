package br.com.helton.projeto_ponto_eletronico.dto;

import br.com.helton.projeto_ponto_eletronico.domain.TipoJornada;

import java.time.LocalDateTime;

public record JornadaParaEdicaoDto(
        TipoJornada tipoJornada,
        LocalDateTime dataEntrada,
        LocalDateTime dataSaida,
        int minutosTrabalhados
) {
}
