package br.com.helton.projeto_ponto_eletronico.dto;

import java.util.List;

public record PontosDoMesDto(
        List<PontoDto> diasDoMes,
        List<FeriadoDto> feriados
) {
}
