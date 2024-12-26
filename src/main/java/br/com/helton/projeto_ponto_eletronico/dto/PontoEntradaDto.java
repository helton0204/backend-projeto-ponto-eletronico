package br.com.helton.projeto_ponto_eletronico.dto;

import br.com.helton.projeto_ponto_eletronico.domain.Ponto;
import br.com.helton.projeto_ponto_eletronico.domain.TipoJornada;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record PontoEntradaDto(
        @NotNull Long idUsuario,
        @NotNull LocalTime dataHora,
        @NotNull TipoJornada tipoJornada
) {
    public PontoEntradaDto(Ponto ponto){
        this(ponto.getUsuario().getId(), ponto.getHoraEntrada(), ponto.getJornada().getTipoJornada());
    }
}
