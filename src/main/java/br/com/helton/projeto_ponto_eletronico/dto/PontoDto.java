package br.com.helton.projeto_ponto_eletronico.dto;

import br.com.helton.projeto_ponto_eletronico.domain.Ponto;
import br.com.helton.projeto_ponto_eletronico.domain.TipoJornada;

import java.time.LocalDate;
import java.time.LocalTime;

public record PontoDto(
        Long idPonto,
        LocalDate data,
        LocalTime horaEntrada,
        LocalTime pausaAlmoco,
        LocalTime retornoAlmoco,
        LocalTime horaSaida,
        LocalTime horasTrabalhadas,
        TipoJornada tipoJornada,
        Integer saldoMinutos
) {
    public PontoDto(Ponto ponto) {
        this(
                ponto.getId(),
                ponto.getData(),
                ponto.getHoraEntrada(),
                ponto.getPausaAlmoco(),
                ponto.getRetornoAlmoco(),
                ponto.getHoraSaida(),
                ponto.getHorasTrabalhadas(),
                ponto.getJornada().getTipoJornada(),
                ponto.getSaldoEmMinutos()
        );
    }
}


